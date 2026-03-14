import React from 'react';
import { useNavigate } from 'react-router-dom';

const LandingPage = () => {
    const navigate = useNavigate();

    return (
        <div style={{ 
            display: 'flex', 
            flexDirection: 'column', 
            alignItems: 'center', 
            justifyContent: 'center', 
            minHeight: '100vh', 
            textAlign: 'center', 
            padding: '0 20px', 
            backgroundColor: '#f8f9fa' 
        }}>
            <div className="card animated-card" style={{ maxWidth: '600px', width: '100%' }}>
                <h1 className="pulse" style={{ color: 'var(--primary)', fontSize: '2.5rem', marginBottom: '20px' }}>
                    Welcome to Placement Compass 🧭
                </h1>
                <p style={{ fontSize: '1.2rem', color: 'var(--gray)', marginBottom: '30px' }}>
                    Your personalised placement filter – no clutter, just opportunities.
                </p>
                <div style={{ display: 'flex', gap: '20px', justifyContent: 'center', flexWrap: 'wrap' }}>
                    <button className="btn btn-primary" onClick={() => navigate('/admin/login')}>
                        Login as Admin
                    </button>
                    <button className="btn btn-success" onClick={() => navigate('/student')}>
                        Continue Free (Student)
                    </button>
                </div>
                <p style={{ marginTop: '30px', color: 'var(--gray)', fontSize: '0.9rem' }}>
                    No signup required (for students)– just filter and explore drives.
                </p>
            </div>
        </div>
    );
};

export default LandingPage;