import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const ResourceList = () => {
    const navigate = useNavigate();
    const [resources, setResources] = useState([]);
    const [colleges, setColleges] = useState([]);
    const [companies, setCompanies] = useState([]);
    const [selectedCollegeId, setSelectedCollegeId] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

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

    const filteredResources = selectedCollegeId
        ? resources.filter(r => r.college?.id === parseInt(selectedCollegeId) || r.collegeId === parseInt(selectedCollegeId))
        : resources;

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
            <button onClick={() => navigate('/student')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Home
            </button>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
                <h2>Interview Resources & Experiences</h2>
                <button onClick={() => navigate('/student/resources/add')} className="btn btn-success">
                    + Add Resource
                </button>
            </div>

            <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '20px' }}>
                <label>Filter by College:</label>
                <select
                    value={selectedCollegeId}
                    onChange={(e) => setSelectedCollegeId(e.target.value)}
                    className="form-control"
                    style={{ width: 'auto' }}
                >
                    <option value="">All Colleges</option>
                    {colleges.map(c => (
                        <option key={c.id} value={c.id}>{c.name}</option>
                    ))}
                </select>
            </div>

            {error && <div className="alert alert-error">{error}</div>}

            {filteredResources.length === 0 ? (
                <p style={{ color: 'var(--gray)' }}>No resources found.</p>
            ) : (
                <div style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))',
                    gap: '20px'
                }}>
                    {filteredResources.map(resource => (
                        <div key={resource.id} className="card" style={{ overflow: 'hidden' }}>
                            <h3 style={{ marginTop: 0, marginBottom: '10px' }}>{resource.title}</h3>
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
                            }}>
                                {resource.content}
                            </p>
                            <div style={{
                                display: 'flex',
                                justifyContent: 'space-between',
                                fontSize: '0.8rem',
                                color: 'var(--gray)',
                                borderTop: '1px solid var(--gray-lighter)',
                                paddingTop: '10px'
                            }}>
                                <span>Posted by: {resource.posterName || 'Anonymous'}</span>
                                <span>{formatDate(resource.createdAt)}</span>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default ResourceList;