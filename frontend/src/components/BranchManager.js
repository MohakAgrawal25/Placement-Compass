import React, { useState, useEffect } from 'react';
import axios from '../axiosConfig';
import { useNavigate } from 'react-router-dom';

const BranchManager = () => {
    const [allBranches, setAllBranches] = useState([]);
    const [selectedBranchIds, setSelectedBranchIds] = useState([]);
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    const collegeId = localStorage.getItem('collegeId');

    useEffect(() => {
        if (!collegeId || collegeId === 'null' || isNaN(parseInt(collegeId))) {
            setError('No valid college associated. You may be a super admin.');
            setLoading(false);
            return;
        }
        fetchData();
    }, [collegeId]);

    const fetchData = async () => {
        setLoading(true);
        setError('');
        try {
            const branchesRes = await axios.get('/branches');
            setAllBranches(branchesRes.data);

            const collegeBranchesRes = await axios.get(`/colleges/${collegeId}/branches`);
            const assignedIds = collegeBranchesRes.data.map(b => b.id);
            setSelectedBranchIds(assignedIds);
        } catch (err) {
            console.error('Error fetching data:', err);
            if (err.response) {
                setError(`Failed to load branches: ${err.response.status} - ${err.response.data?.message || err.response.statusText}`);
            } else if (err.request) {
                setError('No response from server. Please check your connection.');
            } else {
                setError('Error: ' + err.message);
            }
        } finally {
            setLoading(false);
        }
    };

    const handleCheckboxChange = (branchId) => {
        setSelectedBranchIds(prev =>
            prev.includes(branchId)
                ? prev.filter(id => id !== branchId)
                : [...prev, branchId]
        );
    };

    const handleSubmit = async () => {
        setSaving(true);
        setError('');
        setSuccess('');
        try {
            await axios.put(`/colleges/${collegeId}/branches`, {
                branchIds: selectedBranchIds
            });
            setSuccess('Branches updated successfully!');
            await fetchData(); // re-fetch to ensure UI consistency
        } catch (err) {
            console.error('Error saving branches:', err);
            if (err.response) {
                setError(`Failed to update branches: ${err.response.status} - ${err.response.data?.message || err.response.statusText}`);
            } else if (err.request) {
                setError('No response from server. Please check your connection.');
            } else {
                setError('Error: ' + err.message);
            }
        } finally {
            setSaving(false);
        }
    };

    const handleBack = () => {
        navigate('/admin/dashboard');
    };

    if (loading) return <div className="container"><p>Loading branches...</p></div>;

    return (
        <div className="container">
            <button onClick={handleBack} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Dashboard
            </button>
            <h2>Manage College Branches</h2>
            {error && <div className="alert alert-error">{error}</div>}
            {success && <div className="alert alert-success">{success}</div>}

            {!error && (
                <>
                    <div style={{
                        display: 'grid',
                        gridTemplateColumns: 'repeat(auto-fill, minmax(250px, 1fr))',
                        gap: '10px',
                        margin: '20px 0',
                        padding: '10px',
                        border: '1px solid var(--gray-lighter)',
                        borderRadius: 'var(--border-radius)',
                        maxHeight: '400px',
                        overflowY: 'auto'
                    }}>
                        {allBranches.map(branch => (
                            <label key={branch.id} className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={selectedBranchIds.includes(branch.id)}
                                    onChange={() => handleCheckboxChange(branch.id)}
                                />
                                {branch.name}
                            </label>
                        ))}
                    </div>

                    <div style={{ display: 'flex', gap: '10px', justifyContent: 'center' }}>
                        <button onClick={handleSubmit} className="btn btn-success" disabled={saving}>
                            {saving ? 'Saving...' : 'Save Changes'}
                        </button>
                        <button onClick={handleBack} className="btn btn-secondary">
                            Back to Dashboard
                        </button>
                    </div>
                </>
            )}

            {error && (error.includes('super admin') || error.includes('Invalid college ID')) && (
                <button onClick={handleBack} className="btn btn-secondary">
                    Back to Dashboard
                </button>
            )}
        </div>
    );
};

export default BranchManager;