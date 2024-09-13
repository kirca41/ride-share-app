import { Visibility, VisibilityOff } from '@mui/icons-material';
import {
    Box,
    Button,
    Container,
    FormControl,
    FormHelperText,
    Grid,
    IconButton,
    InputAdornment,
    InputLabel,
    OutlinedInput,
    TextField,
    Typography
} from '@mui/material';
import React, { ChangeEvent, FormEvent, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { UserLogin } from '../../interfaces/UserLogin';
import { login } from '../../services/authService';

interface FormErrors {
    username?: string;
    password?: string;
}

const Login: React.FC = () => {
    const [formData, setFormData] = useState<UserLogin>({
        username: '',
        password: '',
    });

    const [formErrors, setFormErrors] = useState<FormErrors>({});
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const navigate = useNavigate();

    const validate = (): FormErrors => {
        const errors: FormErrors = {};

        if (!formData.username) {
            errors.username = "Email is required";
        }

        if (!formData.password) {
            errors.password = "Password is required";
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
            const { jwt } = await login(formData);
            localStorage.setItem('jwt', jwt);
            navigate('/');
        }
    };

    const handleClickShowPassword = (): void => {
        setShowPassword(!showPassword);
    };

    return (
        <Container maxWidth="sm">
            <Box sx={{ mt: 8 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Login
                </Typography>
                <form onSubmit={handleSubmit}>
                    <Grid container spacing={2}>
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
                            <Button
                                type="submit"
                                variant="contained"
                                color="primary"
                                fullWidth
                            >
                                Login
                            </Button>
                        </Grid>
                        <Grid item xs={12}>
                            <Link to='/register' style={{ textDecoration: 'none', color: 'black' }}>
                                <Button
                                    type="button"
                                    variant="contained"
                                    color="secondary"
                                    fullWidth
                                >
                                    Register
                                </Button>
                            </Link>
                        </Grid>
                    </Grid>
                </form>
            </Box>
        </Container>
    );
};

export default Login;
