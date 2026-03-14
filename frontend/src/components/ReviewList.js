import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const ReviewList = () => {
    const navigate = useNavigate();
    const [reviews, setReviews] = useState([]);
    const [colleges, setColleges] = useState([]);
    const [companies, setCompanies] = useState([]);
    const [selectedCollegeId, setSelectedCollegeId] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

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

    const filteredReviews = selectedCollegeId
        ? reviews.filter(r => r.college?.id === parseInt(selectedCollegeId) || r.collegeId === parseInt(selectedCollegeId))
        : reviews;

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
            <button onClick={() => navigate('/student')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Home
            </button>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
                <h2>Company Reviews</h2>
                <button onClick={() => navigate('/student/reviews/add')} className="btn btn-success">
                    + Add Review
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

            {filteredReviews.length === 0 ? (
                <p style={{ color: 'var(--gray)' }}>No reviews found.</p>
            ) : (
                <div style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))',
                    gap: '20px'
                }}>
                    {filteredReviews.map(review => (
                        <div key={review.id} className="card" style={{ display: 'flex', flexDirection: 'column' }}>
                            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '10px' }}>
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
                                maxHeight: '100px',
                                overflowY: 'auto',
                                wordWrap: 'break-word',
                                whiteSpace: 'pre-wrap'
                            }}>
                                "{review.feedback}"
                            </p>
                            <div style={{
                                display: 'flex',
                                justifyContent: 'space-between',
                                fontSize: '0.8rem',
                                color: 'var(--gray)',
                                borderTop: '1px solid var(--gray-lighter)',
                                paddingTop: '10px'
                            }}>
                                <span>Posted by: {review.posterName || 'Anonymous'}</span>
                                <span>{formatDate(review.createdAt)}</span>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default ReviewList;