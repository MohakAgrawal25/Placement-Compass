import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const AddCompany = () => {
    const [companyName, setCompanyName] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!companyName.trim()) {
            setError('Company name is required');
            return;
        }

        setLoading(true);
        setError('');
        setSuccess(false);

        try {
            await axios.post('/companies', { name: companyName.trim() });
            setSuccess(true);
            setCompanyName('');
            setTimeout(() => {
                navigate('/admin/companies');
            }, 1500);
        } catch (err) {
            if (err.response && err.response.status === 409) {
                setError('Company already exists');
            } else {
                setError('Failed to add company. Please try again.');
            }
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleCancel = () => {
        navigate('/admin/companies');
    };

    return (
        <div className="container">
            <button onClick={() => navigate('/admin/companies')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Company List
            </button>
            <h2>Add New Company</h2>
            <form onSubmit={handleSubmit} className="card" style={{ marginTop: '20px' }}>
                <div className="form-group">
                    <label className="form-label" htmlFor="companyName">Company Name:</label>
                    <input
                        type="text"
                        id="companyName"
                        value={companyName}
                        onChange={(e) => setCompanyName(e.target.value)}
                        className="form-control"
                        disabled={loading}
                        autoFocus
                    />
                </div>

                {error && <div className="alert alert-error">{error}</div>}
                {success && <div className="alert alert-success">Company added successfully! Redirecting...</div>}

                <div style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end', marginTop: '20px' }}>
                    <button type="submit" className="btn btn-success" disabled={loading}>
                        {loading ? 'Adding...' : 'Add Company'}
                    </button>
                    <button type="button" onClick={handleCancel} className="btn btn-secondary" disabled={loading}>
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
};

export default AddCompany;