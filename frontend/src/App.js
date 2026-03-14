import React, { useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import LandingPage from './components/LandingPage';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import StudentHome from './components/StudentHome';
import StudentFilter from './components/StudentFilter';
import AddReview from './components/AddReview';
import AddResource from './components/AddResource';
import ReviewList from './components/ReviewList';          // Student view
import ResourceList from './components/ResourceList';      // Student view
import Visualize from './components/Visualize';
import AdminReviewList from './components/AdminReviewList'; // Admin view with delete
import AdminResourceList from './components/AdminResourceList'; // Admin view with delete

import './App.css';
// Admin management components
import CompanyList from './components/CompanyList';
import AddCompany from './components/AddCompany';
import LocationList from './components/LocationList';
import AddLocation from './components/AddLocation';
import DriveList from './components/DriveList';
import DriveForm from './components/DriveForm';
import BranchManager from './components/BranchManager';
import SkillList from './components/SkillList';
import AddSkill from './components/AddSkill';

// PrivateRoute to protect admin routes
const PrivateRoute = ({ children }) => {
    const token = localStorage.getItem('token');
    return token ? children : <Navigate to="/admin/login" />;
};

function App() {
    const [token, setToken] = useState(localStorage.getItem('token'));

    return (
        <BrowserRouter>
            <Routes>
                {/* Public routes */}
                <Route path="/" element={<LandingPage />} />
                <Route path="/admin/login" element={<Login setToken={setToken} />} />

                {/* Student routes */}
                <Route path="/student" element={<StudentHome />} />
                <Route path="/student/drives" element={<StudentFilter />} />
                <Route path="/student/reviews" element={<ReviewList />} />
                <Route path="/student/reviews/add" element={<AddReview />} />
                <Route path="/student/resources" element={<ResourceList />} />
                <Route path="/student/resources/add" element={<AddResource />} />
                <Route path="/student/visualize" element={<Visualize />} />

                {/* Protected admin routes */}
                <Route
                    path="/admin/dashboard"
                    element={
                        <PrivateRoute>
                            <Dashboard />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/admin/companies"
                    element={
                        <PrivateRoute>
                            <CompanyList />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/admin/companies/add"
                    element={
                        <PrivateRoute>
                            <AddCompany />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/admin/locations"
                    element={
                        <PrivateRoute>
                            <LocationList />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/admin/locations/add"
                    element={
                        <PrivateRoute>
                            <AddLocation />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/admin/drives"
                    element={
                        <PrivateRoute>
                            <DriveList />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/admin/drives/add"
                    element={
                        <PrivateRoute>
                            <DriveForm />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/admin/drives/edit/:id"
                    element={
                        <PrivateRoute>
                            <DriveForm />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/admin/branches"
                    element={
                        <PrivateRoute>
                            <BranchManager />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/admin/skills"
                    element={
                        <PrivateRoute>
                            <SkillList />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/admin/skills/add"
                    element={
                        <PrivateRoute>
                            <AddSkill />
                        </PrivateRoute>
                    }
                />
                {/* Admin reviews */}
                <Route
                    path="/admin/reviews"
                    element={
                        <PrivateRoute>
                            <AdminReviewList />
                        </PrivateRoute>
                    }
                />
                {/* Admin resources */}
                <Route
                    path="/admin/resources"
                    element={
                        <PrivateRoute>
                            <AdminResourceList />
                        </PrivateRoute>
                    }
                />

                {/* Catch‑all – redirect to landing page */}
                <Route path="*" element={<Navigate to="/" />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;