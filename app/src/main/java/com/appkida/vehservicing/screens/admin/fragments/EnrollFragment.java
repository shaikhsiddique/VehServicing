package com.appkida.vehservicing.screens.admin.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appkida.vehservicing.R;
import com.appkida.vehservicing.adapter.FacilitiesAdapter;
import com.appkida.vehservicing.adapter.ServiceAdapter;
import com.appkida.vehservicing.model.Facility;
import com.appkida.vehservicing.model.Service;
import com.appkida.vehservicing.model.ServicingCenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EnrollFragment extends Fragment {

    private TextInputLayout serviceNameLayout, addressLayout, phoneNumberLayout, emailLayout,
            openingHoursLayout, managerNameLayout, managerEmailLayout;

    private TextInputEditText serviceNameEditText, addressEditText, phoneNumberEditText,
            openingHoursEditText, managerNameEditText, managerEmailEditText;
    TextView emailEditText;

    private RecyclerView servicesRecyclerView;
    private RecyclerView facilitiesRecyclerView;

    private Button enrollServiceCenterButton;

    private DatabaseReference databaseReference;

    private ServiceAdapter serviceAdapter;
    private FacilitiesAdapter facilitiesAdapter;
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageView;
    Button chooseFileButton;
    private StorageReference storageReference;


    public EnrollFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static EnrollFragment newInstance(String param1, String param2) {
        EnrollFragment fragment = new EnrollFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enroll, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("images"); // "images" is the folder name where you want to store your images

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("service_centers");

        imageView = view.findViewById(R.id.imageview);
        chooseFileButton = view.findViewById(R.id.choosefile);

        chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Initialize components
        serviceNameEditText = view.findViewById(R.id.serviceCenterName);
        addressEditText = view.findViewById(R.id.serviceCenterAddress);
        phoneNumberEditText = view.findViewById(R.id.serviceCenterPhoneNumber);
        emailEditText = view.findViewById(R.id.mail);
        String userEmail = retrieveEmailFromSharedPreferences();
        emailEditText.setText(userEmail);
        openingHoursEditText = view.findViewById(R.id.openingHours);
        managerNameEditText = view.findViewById(R.id.managerName);
        managerEmailEditText = view.findViewById(R.id.managerEmail);

        servicesRecyclerView = view.findViewById(R.id.servicesRecyclerView);
        facilitiesRecyclerView = view.findViewById(R.id.facilitiesRecyclerView);

        List<Service> servicesList = getServicesList();
        List<Facility> facilitiesList = getFacilitiesList();

        serviceAdapter = new ServiceAdapter(servicesList);
        facilitiesAdapter = new FacilitiesAdapter(facilitiesList);

        servicesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        facilitiesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        servicesRecyclerView.setAdapter(serviceAdapter);
        facilitiesRecyclerView.setAdapter(facilitiesAdapter);

        enrollServiceCenterButton = view.findViewById(R.id.enrollServiceCenterButton);

        enrollServiceCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addServiceCenterData();
            }
        });
    }


    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            if (data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();
                displaySelectedImage(selectedImageUri);
            }
        }
    }

    private void displaySelectedImage(Uri imageUri) {
        try {
            InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imageView.setVisibility(View.VISIBLE);
            chooseFileButton.setVisibility(View.GONE);
            imageView.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void addServiceCenterData() {


        String serviceName = serviceNameEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String openingHours = openingHoursEditText.getText().toString();
        String managerName = managerNameEditText.getText().toString();
        String managerEmail = managerEmailEditText.getText().toString();

        List<Facility> selectedFacilities = facilitiesAdapter.getSelectedFacilities();
        List<Service> selectedServices = serviceAdapter.getSelectedServices();

        if (!serviceName.isEmpty() && !address.isEmpty() && !phoneNumber.isEmpty()
                && !email.isEmpty() && !openingHours.isEmpty()
                && !managerName.isEmpty() && !managerEmail.isEmpty()
                && !selectedServices.isEmpty() && !selectedFacilities.isEmpty()) {

            List<String> facilityNames = new ArrayList<>();
            for (Facility facility : selectedFacilities) {
                facilityNames.add(facility.getName());
            }

            List<String> serviceNames = new ArrayList<>();
            for (Service service : selectedServices) {
                serviceNames.add(service.getName());
            }

            ServicingCenter serviceCenter = new ServicingCenter(serviceName, address, phoneNumber,
                    email, openingHours, managerName, managerEmail,"", facilityNames, serviceNames);

            Uri imageUri = getImageUriFromImageView(R.id.imageview);
            if(imageUri!=null)
                imageView.setVisibility(View.VISIBLE);
            uploadImage(imageUri, serviceName, address, phoneNumber, email, openingHours, managerName, managerEmail, facilityNames, serviceNames);
        }
        else {
            Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
        }
    }
    private Uri getImageUriFromImageView(int imageViewId) {
        ImageView imageView = getView().findViewById(imageViewId);
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();


        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "Image Description", null);
        return Uri.parse(path);
    }

    private void uploadImage(Uri imageUri, final String serviceName, final String address, final String phoneNumber,
                             final String email, final String openingHours, final String managerName, final String managerEmail,
                             final List<String> facilityNames, final List<String> serviceNames) {
        if (imageUri != null) {

            final StorageReference imageRef = storageReference.child(imageUri.getLastPathSegment());


            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {

                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {

                            ServicingCenter serviceCenter = new ServicingCenter(serviceName, address, phoneNumber,
                                    email, openingHours, managerName, managerEmail, uri.toString(), facilityNames, serviceNames);

                            // Save the ServicingCenter object to the Realtime Database
                            databaseReference.push().setValue(serviceCenter);

                            clearFields();
                            imageView.setVisibility(View.GONE);
                            Toast.makeText(requireContext(), "Enrolled Successfully", Toast.LENGTH_SHORT).show();
                            saveServiceNameToSharedPreferences(serviceName);

                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        }
        else {
            ServicingCenter serviceCenter = new ServicingCenter(serviceName, address, phoneNumber,
                    email, openingHours, managerName, managerEmail, "", facilityNames, serviceNames);

            // Save the ServicingCenter object to the Realtime Database
            databaseReference.push().setValue(serviceCenter);

            clearFields();
            imageView.setVisibility(View.GONE);

            Toast.makeText(requireContext(), "Enrolled Successfully", Toast.LENGTH_SHORT).show();
            saveServiceNameToSharedPreferences(serviceName);

        }
    }

    private void saveServiceNameToSharedPreferences(String serviceName) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("serviceName", serviceName);
        editor.apply();
    }
    private void clearFields() {
        serviceNameEditText.setText("");
        addressEditText.setText("");
        phoneNumberEditText.setText("");
        emailEditText.setText("");
        openingHoursEditText.setText("");
        managerNameEditText.setText("");
        managerEmailEditText.setText("");
        serviceAdapter.clearSelections();
        facilitiesAdapter.clearSelections();
        chooseFileButton.setVisibility(View.VISIBLE);

    }
    private String retrieveEmailFromSharedPreferences() {
        SharedPreferences sharedPreferences =requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userEmail", "");
    }
    private List<Service> getServicesList() {
        List<Service> servicesList = new ArrayList<>();

        //services
        servicesList.add(new Service("Oil Change", R.drawable.oil_change));
        servicesList.add(new Service("Brake Inspection", R.drawable.brake));
        servicesList.add(new Service("Tire Rotation", R.drawable.tyree));
        servicesList.add(new Service("Engine Diagnostics", R.drawable.engine));
        servicesList.add(new Service("Transmission Service", R.drawable.tire_rotation));
        servicesList.add(new Service("Battery Replacement", R.drawable.battery));

        return servicesList;
    }

    private List<Facility> getFacilitiesList() {
        List<Facility> facilitiesList = new ArrayList<>();

        // facilities
        facilitiesList.add(new Facility("Comfortable Waiting Area", R.drawable.waiting_area));
        facilitiesList.add(new Facility("Free Wi-Fi", R.drawable.free_wifi));
        facilitiesList.add(new Facility("Coffee Lounge", R.drawable.claean));
        facilitiesList.add(new Facility("Kids Play Area", R.drawable.kids_play));
        facilitiesList.add(new Facility("Clean Restrooms", R.drawable.clean));
        facilitiesList.add(new Facility("Shuttle Service", R.drawable.shuttle_services));

        return facilitiesList;
    }
}
