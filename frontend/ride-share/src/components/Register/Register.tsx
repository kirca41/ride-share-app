import React, { useState, ChangeEvent, FormEvent } from 'react';
import {
    Button,
    TextField,
    Grid,
    Container,
    Typography,
    Box,
    FormControl,
    InputLabel,
    InputAdornment,
    IconButton,
    OutlinedInput,
    FormHelperText
} from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import { register } from '../../services/authService';
import { useNavigate } from 'react-router-dom';
import { UserRegister } from '../../interfaces/UserRegister';

interface FormErrors {
    firstName?: string;
    lastName?: string;
    username?: string;
    mobileNumber?: string;
    password?: string;
    confirmPassword?: string;
}

const Register: React.FC = () => {
    const [formData, setFormData] = useState<UserRegister>({
        firstName: '',
        lastName: '',
        username: '',
        mobileNumber: '',
        password: '',
        confirmPassword: ''
    });

    const [formErrors, setFormErrors] = useState<FormErrors>({});
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState<boolean>(false);
    const navigate = useNavigate();

    const validate = (): FormErrors => {
        const errors: FormErrors = {};

        if (!formData.firstName) {
            errors.firstName = "First name is required";
        }
        if (!formData.lastName) {
            errors.lastName = "Last name is required";
        }
        if (!formData.username) {
            errors.username = "Email is required";
        } else if (!/\S+@\S+\.\S+/.test(formData.username)) {
            errors.username = "Email is invalid";
        }
        if (!formData.mobileNumber) {
            errors.mobileNumber = "Mobile number is required";
        } else if (!/^\d{10}$/.test(formData.mobileNumber)) {
            errors.mobileNumber = "Mobile number is invalid";
        }
        if (!formData.password) {
            errors.password = "Password is required";
        } else if (formData.password.length < 6) {
            errors.password = "Password must be at least 6 characters";
        }
        if (!formData.confirmPassword) {
            errors.confirmPassword = "Confirm password is required";
        } else if (formData.password !== formData.confirmPassword) {
            errors.confirmPassword = "Passwords do not match";
        }

        return errors;
    };

    const handleChange = (e: ChangeEvent<HTMLInputElement>): void => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const errors = validate();
        setFormErrors(errors);

        if (Object.keys(errors).length === 0) {
            const { jwt } = await register(formData);
            localStorage.setItem('jwt', jwt);
            navigate('/');
        }
    };

    const handleClickShowPassword = (): void => {
        setShowPassword(!showPassword);
    };

    const handleClickShowConfirmPassword = (): void => {
        setShowConfirmPassword(!showConfirmPassword);
    };

    return (
        <Container maxWidth="sm">
            <Box sx={{ mt: 8 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Register
                </Typography>
                <form onSubmit={handleSubmit}>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                label="First Name"
                                name="firstName"
                                variant="outlined"
                                fullWidth
                                value={formData.firstName}
                                onChange={handleChange}
                                required
                                error={!!formErrors.firstName}
                                helperText={formErrors.firstName}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                label="Last Name"
                                name="lastName"
                                variant="outlined"
                                fullWidth
                                value={formData.lastName}
                                onChange={handleChange}
                                required
                                error={!!formErrors.lastName}
                                helperText={formErrors.lastName}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                label="Email"
                                name="username"
                                type="email"
                                variant="outlined"
                                fullWidth
                                value={formData.username}
                                onChange={handleChange}
                                required
                                error={!!formErrors.username}
                                helperText={formErrors.username}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                label="Mobile Number"
                                name="mobileNumber"
                                type="tel"
                                variant="outlined"
                                fullWidth
                                value={formData.mobileNumber}
                                onChange={handleChange}
                                required
                                error={!!formErrors.mobileNumber}
                                helperText={formErrors.mobileNumber}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <FormControl variant="outlined" fullWidth required error={!!formErrors.password}>
                                <InputLabel>Password</InputLabel>
                                <OutlinedInput
                                    name="password"
                                    type={showPassword ? 'text' : 'password'}
                                    value={formData.password}
                                    onChange={handleChange}
                                    endAdornment={
                                        <InputAdornment position="end">
                                            <IconButton
                                                onClick={handleClickShowPassword}
                                                edge="end"
                                            >
                                                {showPassword ? <VisibilityOff /> : <Visibility />}
                                            </IconButton>
                                        </InputAdornment>
                                    }
                                    label="Password"
                                />
                                {formErrors.password && (
                                    <FormHelperText>{formErrors.password}</FormHelperText>
                                )}
                            </FormControl>
                        </Grid>
                        <Grid item xs={12}>
                            <FormControl variant="outlined" fullWidth required error={!!formErrors.confirmPassword}>
                                <InputLabel>Confirm Password</InputLabel>
                                <OutlinedInput
                                    name="confirmPassword"
                                    type={showConfirmPassword ? 'text' : 'password'}
                                    value={formData.confirmPassword}
                                    onChange={handleChange}
                                    endAdornment={
                                        <InputAdornment position="end">
                                            <IconButton
                                                onClick={handleClickShowConfirmPassword}
                                                edge="end"
                                            >
                                                {showConfirmPassword ? <VisibilityOff /> : <Visibility />}
                                            </IconButton>
                                        </InputAdornment>
                                    }
                                    label="Confirm Password"
                                />
                                {formErrors.confirmPassword && (
                                    <FormHelperText>{formErrors.confirmPassword}</FormHelperText>
                                )}
                            </FormControl>
                        </Grid>
                        <Grid item xs={12}>
                            <Button
                                type="submit"
                                variant="contained"
                                color="primary"
                                fullWidth
                            >
                                Register
                            </Button>
                        </Grid>
                    </Grid>
                </form>
            </Box>
        </Container>
    );
};

export default Register;
