import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const CompanyList = () => {
    const [companies, setCompanies] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetchCompanies();
    }, [searchTerm]);

    const fetchCompanies = async () => {
        setLoading(true);
        try {
            const url = searchTerm
                ? `/companies?search=${encodeURIComponent(searchTerm)}`
                : '/companies';
            const response = await axios.get(url);
            setCompanies(response.data);
            setError('');
        } catch (err) {
            setError('Failed to load companies');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleAddNew = () => {
        navigate('/admin/companies/add');
    };

    return (
        <div className="container">
            <button onClick={() => navigate('/admin/dashboard')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Dashboard
            </button>
            <h2>Company List</h2>

            <div style={{ display: 'flex', gap: '10px', marginBottom: '20px', flexWrap: 'wrap' }}>
                <input
                    type="text"
                    placeholder="Search companies..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="form-control"
                    style={{ flex: 1, minWidth: '200px' }}
                />
                <button onClick={handleAddNew} className="btn btn-success">
                    + Add New Company
                </button>
            </div>

            {loading && <p>Loading companies...</p>}
            {error && <div className="alert alert-error">{error}</div>}

            {!loading && !error && (
                <table className="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Company Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        {companies.length === 0 ? (
                            <tr>
                                <td colSpan="2" style={{ textAlign: 'center', padding: '20px', color: 'var(--gray)' }}>
                                    No companies found
                                </td>
                            </tr>
                        ) : (
                            companies.map(company => (
                                <tr key={company.id}>
                                    <td>{company.id}</td>
                                    <td>{company.name}</td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default CompanyList;