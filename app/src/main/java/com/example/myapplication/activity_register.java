package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class activity_register extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonRegister;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        apiService = ApiManager.getApiService();


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = editTextUsername.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                RegistrationRequest registrationRequest = new RegistrationRequest(username, email, password);

                registerUser(registrationRequest);
            }
        });
    }

    private void registerUser(RegistrationRequest registrationRequest) {
        Log.d("RegistrationRequest", "Username: " + registrationRequest.getName() +
                ", Email: " + registrationRequest.getEmail() +
                ", Password: " + registrationRequest.getPassword());

        Call<RegistrationResponse> registerCall = apiService.registerUser(registrationRequest);

        registerCall.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(activity_register.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(activity_register.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Log.e("RegistrationError", "Error en la solicitud: " + t.getMessage());
                Toast.makeText(activity_register.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }

        });
    }
}

