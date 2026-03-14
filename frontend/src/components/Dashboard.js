import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const Dashboard = () => {
    const navigate = useNavigate();
    const [adminInfo, setAdminInfo] = useState({ username: '', collegeName: '' });
    const [stats, setStats] = useState({
        companies: 0,
        locations: 0,
        drives: 0,
        skills: 0
    });
    const [loadingStats, setLoadingStats] = useState(true);

    useEffect(() => {
        const fetchAdminInfo = async () => {
            const username = localStorage.getItem('username') || 'Admin';
            const collegeId = localStorage.getItem('collegeId');

            if (collegeId && collegeId !== 'null') {
                try {
                    const response = await axios.get(`/colleges/${collegeId}`);
                    setAdminInfo({
                        username,
                        collegeName: response.data.name
                    });
                } catch (err) {
                    console.error('Error fetching college name:', err);
                    setAdminInfo({
                        username,
                        collegeName: 'Unknown College'
                    });
                }
            } else {
                setAdminInfo({
                    username,
                    collegeName: 'Super Admin'
                });
            }
        };
        fetchAdminInfo();
    }, []);

    // Fetch stats
    useEffect(() => {
        const fetchStats = async () => {
            setLoadingStats(true);
            try {
                const [companiesRes, locationsRes, drivesRes, skillsRes] = await Promise.all([
                    axios.get('/companies'),
                    axios.get('/locations'),
                    axios.get('/drives'),
                    axios.get('/skills')
                ]);
                setStats({
                    companies: Array.isArray(companiesRes.data) ? companiesRes.data.length : 0,
                    locations: Array.isArray(locationsRes.data) ? locationsRes.data.length : 0,
                    drives: Array.isArray(drivesRes.data) ? drivesRes.data.length : 0,
                    skills: Array.isArray(skillsRes.data) ? skillsRes.data.length : 0
                });
            } catch (err) {
                console.error('Error fetching stats:', err);
            } finally {
                setLoadingStats(false);
            }
        };
        fetchStats();
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('collegeId');
        localStorage.removeItem('username');
        navigate('/');
    };

    return (
        <div style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column', backgroundColor: 'var(--light)' }}>
            <header className="header">
                <h1 className="header-title">Placement Compass Admin</h1>
                <div>
                    <span style={{ marginRight: '20px', fontWeight: 'bold' }}>
                        👤 {adminInfo.username} ({adminInfo.collegeName})
                    </span>
                    <button className="btn btn-danger" onClick={handleLogout}>
                        Logout
                    </button>
                </div>
            </header>

            <div className="container">
                {/* Welcome Card – compact */}
                <div className="card animated-card" style={{ textAlign: 'center', marginBottom: '20px', padding: '15px' }}>
                    <h2 style={{ color: 'var(--primary)', marginBottom: '5px' }}>Welcome back, {adminInfo.username}!</h2>
                    <p style={{ color: 'var(--gray)', margin: 0 }}>Here's what's happening with your placement data today.</p>
                </div>

                {/* Statistics Cards – side by side in one row */}
                <div style={{ marginBottom: '30px' }}>
                    <h3 style={{ marginBottom: '15px', color: 'var(--dark)' }}>Overview</h3>
                    <div style={{
                        display: 'grid',
                        gridTemplateColumns: 'repeat(4, 1fr)',
                        gap: '12px',
                        '@media (max-width: 768px)': {
                            gridTemplateColumns: '1fr'
                        }
                    }}>
                        <StatCard title="Total Companies" value={stats.companies} icon="🏢" loading={loadingStats} />
                        <StatCard title="Total Locations" value={stats.locations} icon="📍" loading={loadingStats} />
                        <StatCard title="Your Placement Drives" value={stats.drives} icon="🚀" loading={loadingStats} />
                        <StatCard title="Total Skills" value={stats.skills} icon="🔧" loading={loadingStats} />
                    </div>
                </div>

                {/* Management Cards – two rows */}
                <div style={{ marginBottom: '30px' }}>
                    <h3 style={{ marginBottom: '15px', color: 'var(--dark)' }}>Management</h3>
                    {/* First row: four cards */}
                    <div style={{
                        display: 'grid',
                        gridTemplateColumns: 'repeat(4, 1fr)',
                        gap: '15px',
                        marginBottom: '15px',
                        '@media (max-width: 768px)': {
                            gridTemplateColumns: '1fr'
                        }
                    }}>
                        <Card title="Companies" description="View and manage company list" onClick={() => navigate('/admin/companies')} />
                        <Card title="Locations" description="Manage work locations" onClick={() => navigate('/admin/locations')} />
                        <Card title="Placement Drives" description="Create and edit placement drives" onClick={() => navigate('/admin/drives')} />
                        <Card title="College Branches" description="Select branches offered by your college" onClick={() => navigate('/admin/branches')} />
                    </div>
                    {/* Second row: three cards */}
                    <div style={{
                        display: 'grid',
                        gridTemplateColumns: 'repeat(3, 1fr)',
                        gap: '15px',
                        '@media (max-width: 768px)': {
                            gridTemplateColumns: '1fr'
                        }
                    }}>
                        <Card title="Skills" description="Manage skill tags" onClick={() => navigate('/admin/skills')} />
                        <Card title="Reviews" description="Moderate company reviews" onClick={() => navigate('/admin/reviews')} />
                        <Card title="Resources" description="Manage interview resources" onClick={() => navigate('/admin/resources')} />
                    </div>
                </div>
            </div>

            <footer className="footer">
                <p>📧 mohakagrawal10@gmail.com  |  📞 +91 8329302182</p>
                <p>Want to register your college? <a href="mohakagrawal10@gmail.com" style={{ color: 'var(--secondary)', textDecoration: 'none' }}>Write to us</a></p>
                <p style={{ fontSize: '0.9rem', marginTop: '10px' }}>© {new Date().getFullYear()} Placement Compass. All rights reserved.</p>
                <p style={{ fontSize: '0.9rem', marginTop: '10px' }}>Made by Mohak Agrawal.</p>
            </footer>
        </div>
    );
};

// Reusable Card for navigation
const Card = ({ title, description, onClick }) => (
    <div className="card" onClick={onClick} style={{ cursor: 'pointer' }}>
        <h3 className="card-title">{title}</h3>
        <p>{description}</p>
    </div>
);

// Statistic card
const StatCard = ({ title, value, icon, loading }) => (
    <div className="stat-card">
        <div style={{ fontSize: '2rem', marginBottom: '10px' }}>{icon}</div>
        <h3>{title}</h3>
        <p>{loading ? '...' : value}</p>
    </div>
);

export default Dashboard;