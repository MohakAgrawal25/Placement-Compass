import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const AddReview = () => {
    const navigate = useNavigate();

    const [colleges, setColleges] = useState([]);
    const [companies, setCompanies] = useState([]);
    const [formData, setFormData] = useState({
        collegeId: '',
        companyId: '',
        rating: 5,
        feedback: '',
        posterName: '',
        driveYear: new Date().getFullYear()
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
        if (!formData.feedback.trim()) {
            setError('Feedback cannot be empty.');
            return;
        }
        setSubmitting(true);
        setError('');
        try {
            const payload = {
                college: { id: parseInt(formData.collegeId) },
                company: { id: parseInt(formData.companyId) },
                rating: parseInt(formData.rating),
                feedback: formData.feedback.trim(),
                posterName: formData.posterName.trim() || null,
                driveYear: formData.driveYear ? parseInt(formData.driveYear) : null
            };
            await axios.post('/reviews', payload);
            setSuccess(true);
            setFormData({
                collegeId: '',
                companyId: '',
                rating: 5,
                feedback: '',
                posterName: '',
                driveYear: new Date().getFullYear()
            });
            setTimeout(() => navigate('/student/reviews'), 2000);
        } catch (err) {
            console.error('Error submitting review:', err);
            setError('Failed to submit review. Please try again.');
        } finally {
            setSubmitting(false);
        }
    };

    if (loading) return <div className="container">Loading...</div>;

    return (
        <div className="container">
            <button onClick={() => navigate('/student/reviews')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Reviews
            </button>
            <h2>Add Company Review</h2>
            <p>Share your interview experience to help fellow students.</p>

            {error && <div className="alert alert-error">{error}</div>}
            {success && <div className="alert alert-success">Review submitted successfully! Redirecting...</div>}

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
                    <label className="form-label">Rating *</label>
                    <select
                        name="rating"
                        value={formData.rating}
                        onChange={handleChange}
                        required
                        className="form-control"
                    >
                        {[5,4,3,2,1].map(num => (
                            <option key={num} value={num}>{num} Star{num !== 1 ? 's' : ''}</option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label className="form-label">Year of Drive (optional)</label>
                    <input
                        type="number"
                        name="driveYear"
                        value={formData.driveYear}
                        onChange={handleChange}
                        min="2000"
                        max={new Date().getFullYear() + 1}
                        className="form-control"
                        placeholder="e.g., 2025"
                    />
                </div>

                <div className="form-group">
                    <label className="form-label">Feedback *</label>
                    <textarea
                        name="feedback"
                        value={formData.feedback}
                        onChange={handleChange}
                        required
                        rows="5"
                        className="form-control"
                        placeholder="Describe your interview experience, selection process, tips, etc."
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
                        {submitting ? 'Submitting...' : 'Submit Review'}
                    </button>
                    <button type="button" onClick={() => navigate('/student/reviews')} className="btn btn-secondary">
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
};

export default AddReview;