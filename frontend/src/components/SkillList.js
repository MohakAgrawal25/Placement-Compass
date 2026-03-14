import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const SkillList = () => {
    const [skills, setSkills] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetchSkills();
    }, [searchTerm]);

    const fetchSkills = async () => {
        setLoading(true);
        try {
            const url = searchTerm
                ? `/skills?search=${encodeURIComponent(searchTerm)}`
                : '/skills';
            const response = await axios.get(url);
            setSkills(response.data);
            setError('');
        } catch (err) {
            setError('Failed to load skills');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleAddNew = () => {
        navigate('/admin/skills/add');
    };

    return (
        <div className="container">
            <button onClick={() => navigate('/admin/dashboard')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Dashboard
            </button>
            <h2>Skill List</h2>

            <div style={{ display: 'flex', gap: '10px', marginBottom: '20px', flexWrap: 'wrap' }}>
                <input
                    type="text"
                    placeholder="Search skills..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="form-control"
                    style={{ flex: 1, minWidth: '200px' }}
                />
                <button onClick={handleAddNew} className="btn btn-success">
                    + Add New Skill
                </button>
            </div>

            {loading && <p>Loading skills...</p>}
            {error && <div className="alert alert-error">{error}</div>}

            {!loading && !error && (
                <table className="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Skill Name</th>
                            <th>Category</th>
                        </tr>
                    </thead>
                    <tbody>
                        {skills.length === 0 ? (
                            <tr>
                                <td colSpan="3" style={{ textAlign: 'center', padding: '20px', color: 'var(--gray)' }}>
                                    No skills found
                                </td>
                            </tr>
                        ) : (
                            skills.map(skill => (
                                <tr key={skill.id}>
                                    <td>{skill.id}</td>
                                    <td>{skill.name}</td>
                                    <td>{skill.category || '-'}</td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default SkillList;