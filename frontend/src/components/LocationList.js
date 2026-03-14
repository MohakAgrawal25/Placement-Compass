import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const LocationList = () => {
    const [locations, setLocations] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetchLocations();
    }, [searchTerm]);

    const fetchLocations = async () => {
        setLoading(true);
        try {
            const url = searchTerm
                ? `/locations?search=${encodeURIComponent(searchTerm)}`
                : '/locations';
            const response = await axios.get(url);
            setLocations(response.data);
            setError('');
        } catch (err) {
            setError('Failed to load locations');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleAddNew = () => {
        navigate('/admin/locations/add');
    };

    return (
        <div className="container">
            <button onClick={() => navigate('/admin/dashboard')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Dashboard
            </button>
            <h2>Location List</h2>

            <div style={{ display: 'flex', gap: '10px', marginBottom: '20px', flexWrap: 'wrap' }}>
                <input
                    type="text"
                    placeholder="Search locations..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="form-control"
                    style={{ flex: 1, minWidth: '200px' }}
                />
                <button onClick={handleAddNew} className="btn btn-success">
                    + Add New Location
                </button>
            </div>

            {loading && <p>Loading locations...</p>}
            {error && <div className="alert alert-error">{error}</div>}

            {!loading && !error && (
                <table className="table">
                    <thead>
                        <tr>
                            <th>Serial No.</th>
                            <th>Location Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        {locations.length === 0 ? (
                            <tr>
                                <td colSpan="2" style={{ textAlign: 'center', padding: '20px', color: 'var(--gray)' }}>
                                    No locations found
                                </td>
                            </tr>
                        ) : (
                            locations.map(location => (
                                <tr key={location.id}>
                                    <td>{location.id}</td>
                                    <td>{location.name}</td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default LocationList;