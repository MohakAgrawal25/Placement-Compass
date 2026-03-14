import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const AddResource = () => {
    const navigate = useNavigate();
    const [colleges, setColleges] = useState([]);
    const [companies, setCompanies] = useState([]);
    const [formData, setFormData] = useState({
        collegeId: '',
        companyId: '',
        title: '',
        content: '',
        posterName: ''
    });
    const [loading, setLoading] = useState(false);
    const [submitting, setSubmitting] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                const [collegesRes, companiesRes] = await Promise.all([
                    axios.get('/colleges'),
                    axios.get('/companies')
                ]);
                setColleges(Array.isArray(collegesRes.data) ? collegesRes.data : []);
                setCompanies(Array.isArray(companiesRes.data) ? companiesRes.data : []);
            } catch (err) {
                console.error('Error fetching data:', err);
                setError('Failed to load required data. Please refresh the page.');
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!formData.collegeId) {
            setError('Please select a college.');
            return;
        }
        if (!formData.companyId) {
            setError('Please select a company.');
            return;
        }
        if (!formData.title.trim()) {
            setError('Title is required.');
            return;
        }
        if (!formData.content.trim()) {
            setError('Content cannot be empty.');
            return;
        }
        setSubmitting(true);
        setError('');
        try {
            const payload = {
                college: { id: parseInt(formData.collegeId) },
                company: { id: parseInt(formData.companyId) },
                title: formData.title.trim(),
                content: formData.content.trim(),
                posterName: formData.posterName.trim() || null
            };
            await axios.post('/resources', payload);
            setSuccess(true);
            setFormData({
                collegeId: '',
                companyId: '',
                title: '',
                content: '',
                posterName: ''
            });
            setTimeout(() => navigate('/student/resources'), 2000);
        } catch (err) {
            console.error('Error submitting resource:', err);
            setError('Failed to submit resource. Please try again.');
        } finally {
            setSubmitting(false);
        }
    };

    if (loading) return <div className="container">Loading...</div>;

    return (
        <div className="container">
            <button onClick={() => navigate('/student/resources')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Resources
            </button>
            <h2>Add Interview Resource / Experience</h2>
            <p>Share your interview experience, tips, or resources with others.</p>

            {error && <div className="alert alert-error">{error}</div>}
            {success && <div className="alert alert-success">Resource submitted successfully! Redirecting...</div>}

            <form onSubmit={handleSubmit} className="card" style={{ marginTop: '20px' }}>
                <div className="form-group">
                    <label className="form-label">College *</label>
                    <select
                        name="collegeId"
                        value={formData.collegeId}
                        onChange={handleChange}
                        required
                        className="form-control"
                    >
                        <option value="">Select your college</option>
                        {colleges.map(c => (
                            <option key={c.id} value={c.id}>{c.name}</option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label className="form-label">Company *</label>
                    <select
                        name="companyId"
                        value={formData.companyId}
                        onChange={handleChange}
                        required
                        className="form-control"
                    >
                        <option value="">Select a company</option>
                        {companies.map(c => (
                            <option key={c.id} value={c.id}>{c.name}</option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label className="form-label">Title *</label>
                    <input
                        type="text"
                        name="title"
                        value={formData.title}
                        onChange={handleChange}
                        required
                        className="form-control"
                        placeholder="e.g., Google Interview Experience 2025"
                    />
                </div>

                <div className="form-group">
                    <label className="form-label">Content *</label>
                    <textarea
                        name="content"
                        value={formData.content}
                        onChange={handleChange}
                        required
                        rows="8"
                        className="form-control"
                        placeholder="Describe your experience, preparation tips, etc."
                    />
                </div>

                <div className="form-group">
                    <label className="form-label">Your Name (optional – leave blank for anonymous)</label>
                    <input
                        type="text"
                        name="posterName"
                        value={formData.posterName}
                        onChange={handleChange}
                        className="form-control"
                        placeholder="Anonymous if left blank"
                    />
                </div>

                <div style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end', marginTop: '20px' }}>
                    <button type="submit" className="btn btn-success" disabled={submitting}>
                        {submitting ? 'Submitting...' : 'Submit Resource'}
                    </button>
                    <button type="button" onClick={() => navigate('/student/resources')} className="btn btn-secondary">
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
};

export default AddResource;