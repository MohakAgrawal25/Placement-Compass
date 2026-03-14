import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const AddLocation = () => {
    const [locationName, setLocationName] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!locationName.trim()) {
            setError('Location name is required');
            return;
        }

        setLoading(true);
        setError('');
        setSuccess(false);

        try {
            await axios.post('/locations', { name: locationName.trim() });
            setSuccess(true);
            setLocationName('');
            setTimeout(() => {
                navigate('/admin/locations');
            }, 1500);
        } catch (err) {
            if (err.response && err.response.status === 409) {
                setError('Location already exists');
            } else {
                setError('Failed to add location. Please try again.');
            }
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleCancel = () => {
        navigate('/admin/locations');
    };

    return (
        <div className="container">
            <button onClick={() => navigate('/admin/locations')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Location List
            </button>
            <h2>Add New Location</h2>
            <form onSubmit={handleSubmit} className="card" style={{ marginTop: '20px' }}>
                <div className="form-group">
                    <label className="form-label" htmlFor="locationName">Location Name:</label>
                    <input
                        type="text"
                        id="locationName"
                        value={locationName}
                        onChange={(e) => setLocationName(e.target.value)}
                        className="form-control"
                        disabled={loading}
                        autoFocus
                    />
                </div>

                {error && <div className="alert alert-error">{error}</div>}
                {success && <div className="alert alert-success">Location added successfully! Redirecting...</div>}

                <div style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end', marginTop: '20px' }}>
                    <button type="submit" className="btn btn-success" disabled={loading}>
                        {loading ? 'Adding...' : 'Add Location'}
                    </button>
                    <button type="button" onClick={handleCancel} className="btn btn-secondary" disabled={loading}>
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
};

export default AddLocation;