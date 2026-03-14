import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const AdminReviewList = () => {
    const navigate = useNavigate();
    const [reviews, setReviews] = useState([]);
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
                const [reviewsRes, collegesRes, companiesRes] = await Promise.all([
                    axios.get('/reviews'),
                    axios.get('/colleges'),
                    axios.get('/companies')
                ]);
                setReviews(Array.isArray(reviewsRes.data) ? reviewsRes.data : []);
                setColleges(Array.isArray(collegesRes.data) ? collegesRes.data : []);
                setCompanies(Array.isArray(companiesRes.data) ? companiesRes.data : []);
            } catch (err) {
                console.error('Error fetching reviews:', err);
                setError('Failed to load reviews.');
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    const handleDelete = async (reviewId) => {
        if (!window.confirm('Are you sure you want to delete this review?')) return;
        setDeleting(true);
        try {
            await axios.delete(`/reviews/${reviewId}`);
            setReviews(prev => prev.filter(r => r.id !== reviewId));
        } catch (err) {
            console.error('Error deleting review:', err);
            alert('Failed to delete review.');
        } finally {
            setDeleting(false);
        }
    };

    const filteredReviews = isSuperAdmin
        ? reviews
        : reviews.filter(r => r.college?.id === parseInt(collegeId) || r.collegeId === parseInt(collegeId));

    const getCollegeName = (review) => {
        if (review.college?.name) return review.college.name;
        if (review.collegeId) {
            const college = colleges.find(c => c.id === review.collegeId);
            return college ? college.name : 'Unknown';
        }
        if (review.college?.id) {
            const college = colleges.find(c => c.id === review.college.id);
            return college ? college.name : 'Unknown';
        }
        return 'Unknown';
    };

    const getCompanyName = (review) => {
        if (review.company?.name) return review.company.name;
        if (review.companyId) {
            const company = companies.find(c => c.id === review.companyId);
            return company ? company.name : 'Unknown';
        }
        if (review.company?.id) {
            const company = companies.find(c => c.id === review.company.id);
            return company ? company.name : 'Unknown';
        }
        return 'Unknown';
    };

    const formatDate = (dateStr) => {
        if (!dateStr) return '-';
        return new Date(dateStr).toLocaleDateString();
    };

    if (loading) return <div className="container">Loading reviews...</div>;

    return (
        <div className="container">
            <button onClick={() => navigate('/admin/dashboard')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Dashboard
            </button>
            <h2>Manage Reviews</h2>
            {error && <div className="alert alert-error">{error}</div>}

            {filteredReviews.length === 0 ? (
                <p style={{ color: 'var(--gray)' }}>No reviews found for your college.</p>
            ) : (
                <div style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))',
                    gap: '20px',
                    marginTop: '20px'
                }}>
                    {filteredReviews.map(review => (
                        <div key={review.id} className="card" style={{ position: 'relative', display: 'flex', flexDirection: 'column' }}>
                            <div className="card-header">
                                <h3 style={{ margin: 0 }}>{getCompanyName(review)}</h3>
                                <span style={{ fontWeight: 'bold', color: 'var(--warning)' }}>⭐ {review.rating}/5</span>
                            </div>
                            <p style={{ color: 'var(--gray)', fontSize: '0.9rem', marginBottom: '5px' }}>
                                College: {getCollegeName(review)}
                            </p>
                            {review.driveYear && <p>Year: {review.driveYear}</p>}
                            <p style={{
                                fontStyle: 'italic',
                                margin: '10px 0',
                                maxHeight: '120px',
                                overflowY: 'auto',
                                wordWrap: 'break-word',
                                whiteSpace: 'pre-wrap',
                                paddingRight: '5px'
                            }}>
                                "{review.feedback}"
                            </p>
                            <div style={{
                                display: 'flex',
                                justifyContent: 'space-between',
                                fontSize: '0.8rem',
                                color: 'var(--gray)',
                                borderTop: '1px solid var(--gray-lighter)',
                                paddingTop: '10px',
                                marginBottom: '40px'
                            }}>
                                <span>Posted by: {review.posterName || 'Anonymous'}</span>
                                <span>{formatDate(review.createdAt)}</span>
                            </div>
                            <button
                                onClick={() => handleDelete(review.id)}
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

export default AdminReviewList;