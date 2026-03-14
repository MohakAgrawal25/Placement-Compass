import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const DriveList = () => {
    const [drives, setDrives] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const collegeId = localStorage.getItem('collegeId'); // may be 'null' for super admin

    useEffect(() => {
        fetchDrives();
    }, []);

    const fetchDrives = async () => {
        setLoading(true);
        try {
            const response = await axios.get('/drives');
            setDrives(response.data);
            setError('');
        } catch (err) {
            setError('Failed to load placement drives');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleAdd = () => {
        navigate('/admin/drives/add');
    };

    const handleEdit = (id) => {
        navigate(`/admin/drives/edit/${id}`);
    };

    const handleDelete = async (id) => {
        if (!window.confirm('Are you sure you want to delete this drive?')) return;
        try {
            await axios.delete(`/drives/${id}`);
            fetchDrives(); // refresh list
        } catch (err) {
            alert('Failed to delete drive');
        }
    };

    const handleBackToDashboard = () => {
        navigate('/admin/dashboard');
    };

    const formatDate = (dateStr) => {
        if (!dateStr) return '-';
        return new Date(dateStr).toLocaleDateString();
    };

    const isSuperAdmin = collegeId === 'null';

    return (
        <div className="container">
            <h2>Placement Drives</h2>
            <div style={{ display: 'flex', gap: '10px', marginBottom: '20px' }}>
                <button onClick={handleBackToDashboard} className="btn btn-secondary">
                    ← Back to Dashboard
                </button>
                <button onClick={handleAdd} className="btn btn-success">
                    + Add New Drive
                </button>
            </div>

            {loading && <p>Loading...</p>}
            {error && <div className="alert alert-error">{error}</div>}

            {!loading && !error && (
                <table className="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            {isSuperAdmin && <th>College</th>}
                            <th>Company</th>
                            <th>Role</th>
                            <th>Year</th>
                            <th>Placement Date</th>
                            <th>Package (LPA)(Min-Max)</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {drives.length === 0 ? (
                            <tr>
                                <td colSpan={isSuperAdmin ? 8 : 7} style={{ textAlign: 'center', padding: '20px', color: 'var(--gray)' }}>
                                    No drives found
                                </td>
                            </tr>
                        ) : (
                            drives.map(drive => (
                                <tr key={drive.id}>
                                    <td>{drive.id}</td>
                                    {isSuperAdmin && <td>{drive.collegeName}</td>}
                                    <td>{drive.companyName}</td>
                                    <td>{drive.role}</td>
                                    <td>{drive.year}</td>
                                    <td>{formatDate(drive.placementDate)}</td>
                                    <td>
                                        {drive.packageMinLpa} - {drive.packageMaxLpa}
                                    </td>
                                    <td>
                                        <button
                                            onClick={() => handleEdit(drive.id)}
                                            style={{
                                                padding: '4px 8px',
                                                backgroundColor: 'var(--primary)',
                                                color: '#fff',
                                                border: 'none',
                                                borderRadius: '4px',
                                                marginRight: '5px',
                                                cursor: 'pointer'
                                            }}
                                        >
                                            Edit
                                        </button>
                                        <button
                                            onClick={() => handleDelete(drive.id)}
                                            style={{
                                                padding: '4px 8px',
                                                backgroundColor: 'var(--danger)',
                                                color: '#fff',
                                                border: 'none',
                                                borderRadius: '4px',
                                                cursor: 'pointer'
                                            }}
                                        >
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default DriveList;