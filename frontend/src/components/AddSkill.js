import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const AddSkill = () => {
    const [skillName, setSkillName] = useState('');
    const [category, setCategory] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!skillName.trim()) {
            setError('Skill name is required');
            return;
        }

        setLoading(true);
        setError('');
        setSuccess(false);

        try {
            await axios.post('/skills', { 
                name: skillName.trim(),
                category: category.trim() || null
            });
            setSuccess(true);
            setSkillName('');
            setCategory('');
            setTimeout(() => {
                navigate('/admin/skills');
            }, 1500);
        } catch (err) {
            if (err.response && err.response.status === 409) {
                setError('Skill already exists');
            } else {
                setError('Failed to add skill. Please try again.');
            }
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleCancel = () => {
        navigate('/admin/skills');
    };

    return (
        <div className="container">
            <button onClick={() => navigate('/admin/skills')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Skill List
            </button>
            <h2>Add New Skill</h2>
            <form onSubmit={handleSubmit} className="card" style={{ marginTop: '20px' }}>
                <div className="form-group">
                    <label className="form-label" htmlFor="skillName">Skill Name:</label>
                    <input
                        type="text"
                        id="skillName"
                        value={skillName}
                        onChange={(e) => setSkillName(e.target.value)}
                        className="form-control"
                        disabled={loading}
                        autoFocus
                    />
                </div>

                <div className="form-group">
                    <label className="form-label" htmlFor="category">Category (optional):</label>
                    <input
                        type="text"
                        id="category"
                        value={category}
                        onChange={(e) => setCategory(e.target.value)}
                        className="form-control"
                        disabled={loading}
                        placeholder="e.g., Programming Language, Database"
                    />
                </div>

                {error && <div className="alert alert-error">{error}</div>}
                {success && <div className="alert alert-success">Skill added successfully! Redirecting...</div>}

                <div style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end', marginTop: '20px' }}>
                    <button type="submit" className="btn btn-success" disabled={loading}>
                        {loading ? 'Adding...' : 'Add Skill'}
                    </button>
                    <button type="button" onClick={handleCancel} className="btn btn-secondary" disabled={loading}>
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
};

export default AddSkill;