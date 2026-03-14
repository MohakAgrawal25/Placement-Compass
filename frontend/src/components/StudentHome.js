import React from 'react';
import { useNavigate } from 'react-router-dom';

const StudentHome = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        navigate('/');
    };

    return (
        <div style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column', backgroundColor: 'var(--light)' }}>
            <header className="header">
                <h1 className="header-title">Placement Compass</h1>
                <button className="btn btn-secondary" onClick={handleLogout}>
                    Exit to Home
                </button>
            </header>

            {/* Welcome & Info Section */}
            <div className="container">
                <div className="card" style={{ textAlign: 'center', marginBottom: '20px', padding: '30px 20px' }}>
                    <h2 style={{ color: 'var(--primary)' }}>Welcome, Student! 🎓</h2>
                    <p style={{ fontSize: '1.1rem', color: 'var(--gray-dark)', marginBottom: '20px' }}>
                        Your ultimate companion for placement preparation – all in one place, completely free.
                    </p>
                    <div style={{
                        backgroundColor: 'var(--light)',
                        padding: '20px',
                        borderRadius: 'var(--border-radius)',
                        textAlign: 'left',
                        maxWidth: '700px',
                        margin: '20px auto 0'
                    }}>
                        <p><strong>✨ What you can do here:</strong></p>
                        <ul style={{ listStyle: 'none', paddingLeft: 0, margin: '10px 0' }}>
                            <li>🔍 <strong>Browse Placement Drives</strong> – Search and filter drives by college, company, branch, location, and more.</li>
                            <li>⭐ <strong>Read & Write Reviews</strong> – See what others say about companies and share your own experience.</li>
                            <li>📘 <strong>Access Interview Resources</strong> – Find tips, experiences, and preparation materials shared by peers.</li>
                            <li>📊 <strong>Explore Placement Insights</strong> – View trends, average packages, and statistics to plan your career.</li>
                        </ul>
                        <p style={{ marginTop: '15px', fontStyle: 'italic', color: 'var(--success)', fontWeight: 'bold' }}>
                            No signup required – just click and explore!
                        </p>
                    </div>
                </div>

                {/* Cards for main actions */}
                <div className="grid">
                    <div className="card" style={{ cursor: 'pointer' }} onClick={() => navigate('/student/drives')}>
                        <h2 className="card-title">📋 View Placement Drives</h2>
                        <p>Browse all upcoming and past placement drives of many colleges. Filter by your preferences.</p>
                    </div>
                    <div className="card" style={{ cursor: 'pointer' }} onClick={() => navigate('/student/reviews')}>
                        <h2 className="card-title">⭐ View Company Reviews</h2>
                        <p>Read honest reviews and experiences shared by fellow students.</p>
                    </div>
                    <div className="card" style={{ cursor: 'pointer' }} onClick={() => navigate('/student/resources')}>
                        <h2 className="card-title">📚 View Interview Resources</h2>
                        <p>Discover interview experiences, preparation tips, and useful resources.</p>
                    </div>
                    <div className="card" style={{ cursor: 'pointer' }} onClick={() => navigate('/student/visualize')}>
                        <h2 className="card-title">📊 Placement Insights</h2>
                        <p>Visualise placement trends, average packages, and other key statistics.</p>
                    </div>
                </div>
            </div>

            <footer className="footer">
                <p>Placement Compass – Helping students navigate placements with clarity.</p>
            </footer>
        </div>
    );
};

export default StudentHome;