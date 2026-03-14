import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const AdminResourceList = () => {
    const navigate = useNavigate();
    const [resources, setResources] = useState([]);
    const [colleges, setColleges] = useState([]);
    const [companies, setCompanies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [deleting, setDeleting] = useState(false);

    const collegeId = localStorage.getItem('collegeId');
    const isSuperAdmin = collegeId === 'null';

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                const [resourcesRes, collegesRes, companiesRes] = await Promise.all([
                    axios.get('/resources'),
                    axios.get('/colleges'),
                    axios.get('/companies')
                ]);
                setResources(Array.isArray(resourcesRes.data) ? resourcesRes.data : []);
                setColleges(Array.isArray(collegesRes.data) ? collegesRes.data : []);
                setCompanies(Array.isArray(companiesRes.data) ? companiesRes.data : []);
            } catch (err) {
                console.error('Error fetching resources:', err);
                setError('Failed to load resources.');
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    const handleDelete = async (resourceId) => {
        if (!window.confirm('Are you sure you want to delete this resource?')) return;
        setDeleting(true);
        try {
            await axios.delete(`/resources/${resourceId}`);
            setResources(prev => prev.filter(r => r.id !== resourceId));
        } catch (err) {
            console.error('Error deleting resource:', err);
            alert('Failed to delete resource.');
        } finally {
            setDeleting(false);
        }
    };

    const filteredResources = isSuperAdmin
        ? resources
        : resources.filter(r => r.college?.id === parseInt(collegeId) || r.collegeId === parseInt(collegeId));

    const getCollegeName = (resource) => {
        if (resource.college?.name) return resource.college.name;
        if (resource.collegeId) {
            const college = colleges.find(c => c.id === resource.collegeId);
            return college ? college.name : 'Unknown';
        }
        if (resource.college?.id) {
            const college = colleges.find(c => c.id === resource.college.id);
            return college ? college.name : 'Unknown';
        }
        return 'Unknown';
    };

    const getCompanyName = (resource) => {
        if (resource.company?.name) return resource.company.name;
        if (resource.companyId) {
            const company = companies.find(c => c.id === resource.companyId);
            return company ? company.name : 'Unknown';
        }
        if (resource.company?.id) {
            const company = companies.find(c => c.id === resource.company.id);
            return company ? company.name : 'Unknown';
        }
        return 'Unknown';
    };

    const formatDate = (dateStr) => {
        if (!dateStr) return '-';
        return new Date(dateStr).toLocaleDateString();
    };

    if (loading) return <div className="container">Loading resources...</div>;

    return (
        <div className="container">
            <button onClick={() => navigate('/admin/dashboard')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Dashboard
            </button>
            <h2>Manage Resources</h2>
            {error && <div className="alert alert-error">{error}</div>}

            {filteredResources.length === 0 ? (
                <p style={{ color: 'var(--gray)' }}>No resources found for your college.</p>
            ) : (
                <div style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))',
                    gap: '20px',
                    marginTop: '20px'
                }}>
                    {filteredResources.map(resource => (
                        <div key={resource.id} className="card" style={{ position: 'relative', overflow: 'hidden' }}>
                            <h3 style={{
                                marginTop: 0,
                                marginBottom: '10px',
                                wordWrap: 'break-word',
                                overflowWrap: 'break-word',
                                hyphens: 'auto',
                                maxWidth: '100%'
                            }}>{resource.title}</h3>
                            <p><strong>Company:</strong> {getCompanyName(resource)}</p>
                            <p><strong>College:</strong> {getCollegeName(resource)}</p>
                            <p style={{
                                maxHeight: '150px',
                                overflowY: 'auto',
                                wordWrap: 'break-word',
                                whiteSpace: 'pre-wrap',
                                backgroundColor: 'var(--light)',
                                padding: '8px',
                                borderRadius: 'var(--border-radius)',
                                margin: '10px 0'
                            }}>{resource.content}</p>
                            <div style={{
                                display: 'flex',
                                justifyContent: 'space-between',
                                fontSize: '0.8rem',
                                color: 'var(--gray)',
                                borderTop: '1px solid var(--gray-lighter)',
                                paddingTop: '10px',
                                marginBottom: '40px'
                            }}>
                                <span>Posted by: {resource.posterName || 'Anonymous'}</span>
                                <span>{formatDate(resource.createdAt)}</span>
                            </div>
                            <button
                                onClick={() => handleDelete(resource.id)}
                                className="btn btn-danger"
                                style={{
                                    position: 'absolute',
                                    bottom: '15px',
                                    right: '15px',
                                    padding: '5px 10px',
                                    fontSize: '0.85rem'
                                }}
                                disabled={deleting}
                            >
                                Delete
                            </button>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default AdminResourceList;