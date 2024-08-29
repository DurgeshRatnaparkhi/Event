package com.rsl.event.entity;



import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// Entity representing a User in the system
@Entity
@Table(name = "users")
public class User implements UserDetails {

    // Primary key for the User entitys
	
    @Id
    @Column(name = "id")
    private String id;

    // First name of the User (not blank)
    @NotBlank(message = "First name is required")
    @Column(name = "firstName")
    private String firstName;

    // Last name of the User (not blank)
    @NotBlank(message = "Last name is required")
    @Column(name = "lastName")
    private String lastName;

    // Contact number of the User (not blank)
    @NotBlank(message = "Phone number is required")
    @Column(name = "phone_No")
    private String phoneNumber;

    // Email address of the User (valid email format)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(name = "mail")
    private String email;

    // Address of the User (not blank)
    @NotBlank(message = "Address is required")
    @Column(name = "address")
    private String address;

    // Name of the firm associated with the User (not blank)
    @NotBlank(message = "Firm name is required")
    @Column(name = "firmName")
    private String firmName;

    // GST Number of the firm associated with the User (can be blank)
    @Column(name = "gstn")
    private String gstin;

    // Password for the User account (with specific format)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
             message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and be at least 8 characters long")
    @NotBlank(message = "Password is required")
    @Column(name = "password")
    private String password;

    // Confirmation password for the User account (matches password)
    @NotBlank(message = "Confirm password is required")
    @Column(name = "confirmPassword")
    private String confirmPassword;

    // Default constructor
    public User() {
        super();
    }

    // Parameterized constructor
    public User(String firstName, String lastName, String phoneNumber, String email, String address, String firmName,
                String gstin, String password, String confirmPassword) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.firmName = firmName;
        this.gstin = gstin;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Getters and setters
    // ...

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	// Unimplemented methods override
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUsername() {
        // Use email as the username
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Account is never expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Account is never locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Credentials are never expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Account is always enabled
        return true;
    }
}
