import React, { useState, useEffect, useMemo } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from '../axiosConfig';

const DriveForm = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const isEditing = !!id;

    const [formData, setFormData] = useState({
        collegeId: '',
        companyId: '',
        role: '',
        minCgpa: '',
        maxAge: '',
        genderPreference: 'Any',
        maxBacklogs: '',
        requiredExperienceMonths: '0',
        pptDate: '',
        placementDate: '',
        year: new Date().getFullYear(),
        noOfPeopleHired: '',
        packageMinLpa: '',
        packageMaxLpa: '',
        noOfRounds: '',
        eligibilityNotes: '',
        description: '',
        branchIds: [],
        skillIds: [],
        locationIds: []
    });

    const [companies, setCompanies] = useState([]);
    const [allBranches, setAllBranches] = useState([]);
    const [collegeBranches, setCollegeBranches] = useState([]);
    const [allSkills, setAllSkills] = useState([]);
    const [allLocations, setAllLocations] = useState([]);
    const [colleges, setColleges] = useState([]);

    const [branchSearch, setBranchSearch] = useState('');
    const [skillSearch, setSkillSearch] = useState('');
    const [locationSearch, setLocationSearch] = useState('');

    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState('');

    const collegeId = localStorage.getItem('collegeId');
    const isSuperAdmin = collegeId === 'null';

    const extractArray = (data, endpointName) => {
        console.log(`Response from ${endpointName}:`, data);
        if (Array.isArray(data)) {
            console.log(`${endpointName}: direct array`);
            return data;
        }
        if (data && data._embedded) {
            for (let key in data._embedded) {
                if (Array.isArray(data._embedded[key])) {
                    console.log(`${endpointName}: found array in _embedded.${key}`);
                    return data._embedded[key];
                }
            }
        }
        if (data && data.content && Array.isArray(data.content)) {
            console.log(`${endpointName}: found array in content`);
            return data.content;
        }
        if (data && typeof data === 'object') {
            for (let key in data) {
                if (Array.isArray(data[key])) {
                    console.log(`${endpointName}: found array in property "${key}"`);
                    return data[key];
                }
            }
        }
        console.warn(`${endpointName}: No array found in response`, data);
        return [];
    };

    const sortByName = (arr) => {
        return arr.slice().sort((a, b) => a.name.localeCompare(b.name));
    };

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError('');
            try {
                const [compRes, branchRes, skillRes, locRes] = await Promise.all([
                    axios.get('/companies'),
                    axios.get('/branches'),
                    axios.get('/skills'),
                    axios.get('/locations')
                ]);

                const companiesData = extractArray(compRes.data, '/companies');
                const branchesData = extractArray(branchRes.data, '/branches');
                const skillsData = extractArray(skillRes.data, '/skills');
                const locationsData = extractArray(locRes.data, '/locations');

                setCompanies(sortByName(companiesData));
                setAllBranches(sortByName(branchesData));
                setAllSkills(sortByName(skillsData));
                setAllLocations(sortByName(locationsData));

                if (!isSuperAdmin && collegeId && collegeId !== 'null') {
                    try {
                        const collegeBranchesRes = await axios.get(`/colleges/${collegeId}/branches`);
                        const collegeBranchesData = extractArray(collegeBranchesRes.data, `/colleges/${collegeId}/branches`);
                        setCollegeBranches(sortByName(collegeBranchesData));
                    } catch (err) {
                        console.error('Error fetching college branches:', err);
                        setCollegeBranches([]);
                    }
                }

                if (isSuperAdmin) {
                    const collegesRes = await axios.get('/colleges');
                    const collegesData = extractArray(collegesRes.data, '/colleges');
                    setColleges(sortByName(collegesData));
                }

                if (isEditing) {
                    await fetchDrive();
                } else {
                    setLoading(false);
                }
            } catch (err) {
                console.error('Error fetching master data:', err);
                setError('Failed to load required data. Please refresh the page.');
                setLoading(false);
            }
        };

        fetchData();
    }, [id, isEditing, isSuperAdmin, collegeId]);

    const fetchDrive = async () => {
        try {
            const response = await axios.get(`/drives/${id}`);
            const drive = response.data;
            console.log('Fetched drive:', drive);
            setFormData({
                collegeId: drive.collegeId,
                companyId: drive.companyId,
                role: drive.role,
                minCgpa: drive.minCgpa || '',
                maxAge: drive.maxAge || '',
                genderPreference: drive.genderPreference,
                maxBacklogs: drive.maxBacklogs || '',
                requiredExperienceMonths: drive.requiredExperienceMonths || '0',
                pptDate: drive.pptDate || '',
                placementDate: drive.placementDate || '',
                year: drive.year,
                noOfPeopleHired: drive.noOfPeopleHired || '',
                packageMinLpa: drive.packageMinLpa || '',
                packageMaxLpa: drive.packageMaxLpa || '',
                noOfRounds: drive.noOfRounds || '',
                eligibilityNotes: drive.eligibilityNotes || '',
                description: drive.description || '',
                branchIds: drive.branches.map(b => b.id),
                skillIds: drive.skills.map(s => s.id),
                locationIds: drive.locations.map(l => l.id)
            });
            setLoading(false);
        } catch (err) {
            console.error('Error fetching drive details:', err);
            setError('Failed to load drive details.');
            setLoading(false);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleBranchToggle = (branchId) => {
        setFormData(prev => {
            const newIds = prev.branchIds.includes(branchId)
                ? prev.branchIds.filter(id => id !== branchId)
                : [...prev.branchIds, branchId];
            console.log('Branch toggled:', branchId, 'new branchIds:', newIds);
            return { ...prev, branchIds: newIds };
        });
    };

    const handleSkillToggle = (skillId) => {
        setFormData(prev => {
            const newIds = prev.skillIds.includes(skillId)
                ? prev.skillIds.filter(id => id !== skillId)
                : [...prev.skillIds, skillId];
            console.log('Skill toggled:', skillId, 'new skillIds:', newIds);
            return { ...prev, skillIds: newIds };
        });
    };

    const handleLocationToggle = (locationId) => {
        setFormData(prev => {
            const newIds = prev.locationIds.includes(locationId)
                ? prev.locationIds.filter(id => id !== locationId)
                : [...prev.locationIds, locationId];
            console.log('Location toggled:', locationId, 'new locationIds:', newIds);
            return { ...prev, locationIds: newIds };
        });
    };

    const branchSource = isSuperAdmin ? allBranches : collegeBranches;

    const filteredBranches = useMemo(() => {
        return branchSource.filter(b =>
            b.name.toLowerCase().includes(branchSearch.toLowerCase())
        );
    }, [branchSource, branchSearch]);

    const filteredSkills = useMemo(() => {
        return allSkills.filter(s =>
            s.name.toLowerCase().includes(skillSearch.toLowerCase()) ||
            (s.category && s.category.toLowerCase().includes(skillSearch.toLowerCase()))
        );
    }, [allSkills, skillSearch]);

    const filteredLocations = useMemo(() => {
        return allLocations.filter(l =>
            l.name.toLowerCase().includes(locationSearch.toLowerCase())
        );
    }, [allLocations, locationSearch]);

    const selectAllBranches = () => {
        const allIds = filteredBranches.map(b => b.id);
        console.log('Select all branches:', allIds);
        setFormData(prev => ({ ...prev, branchIds: allIds }));
    };
    const clearBranches = () => {
        console.log('Clear branches');
        setFormData(prev => ({ ...prev, branchIds: [] }));
    };

    const selectAllSkills = () => {
        const allIds = filteredSkills.map(s => s.id);
        console.log('Select all skills:', allIds);
        setFormData(prev => ({ ...prev, skillIds: allIds }));
    };
    const clearSkills = () => {
        console.log('Clear skills');
        setFormData(prev => ({ ...prev, skillIds: [] }));
    };

    const selectAllLocations = () => {
        const allIds = filteredLocations.map(l => l.id);
        console.log('Select all locations:', allIds);
        setFormData(prev => ({ ...prev, locationIds: allIds }));
    };
    const clearLocations = () => {
        console.log('Clear locations');
        setFormData(prev => ({ ...prev, locationIds: [] }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setSaving(true);
        setError('');

        const payload = {
            ...formData,
            collegeId: isSuperAdmin ? (formData.collegeId || null) : undefined,
            minCgpa: formData.minCgpa === '' ? null : parseFloat(formData.minCgpa),
            maxAge: formData.maxAge === '' ? null : parseInt(formData.maxAge),
            maxBacklogs: formData.maxBacklogs === '' ? null : parseInt(formData.maxBacklogs),
            requiredExperienceMonths: parseInt(formData.requiredExperienceMonths) || 0,
            year: parseInt(formData.year),
            noOfPeopleHired: formData.noOfPeopleHired === '' ? null : parseInt(formData.noOfPeopleHired),
            packageMinLpa: formData.packageMinLpa === '' ? null : parseFloat(formData.packageMinLpa),
            packageMaxLpa: formData.packageMaxLpa === '' ? null : parseFloat(formData.packageMaxLpa),
            noOfRounds: formData.noOfRounds === '' ? null : parseInt(formData.noOfRounds),
            pptDate: formData.pptDate || null,
            placementDate: formData.placementDate || null,
            genderPreference: formData.genderPreference
        };

        console.log('Submitting payload:', payload);

        try {
            if (isEditing) {
                await axios.put(`/drives/${id}`, payload);
            } else {
                await axios.post('/drives', payload);
            }
            navigate('/admin/drives');
        } catch (err) {
            console.error('Error saving drive:', err);
            setError('Failed to save drive. Please try again.');
        } finally {
            setSaving(false);
        }
    };

    if (loading) return <div className="container">Loading...</div>;

    return (
        <div className="container">
            <h2>{isEditing ? 'Edit' : 'Add'} Placement Drive</h2>
            {error && <div className="alert alert-error">{error}</div>}
            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
                {isSuperAdmin && (
                    <div className="form-group">
                        <label className="form-label">College</label>
                        <select
                            name="collegeId"
                            value={formData.collegeId}
                            onChange={handleChange}
                            required
                            className="form-control"
                        >
                            <option value="">Select College</option>
                            {colleges.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                        </select>
                    </div>
                )}

                <div className="form-group">
                    <label className="form-label">Company</label>
                    <select
                        name="companyId"
                        value={formData.companyId}
                        onChange={handleChange}
                        required
                        className="form-control"
                    >
                        <option value="">Select Company</option>
                        {companies.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                    </select>
                </div>

                <div className="form-group">
                    <label className="form-label">Role</label>
                    <input
                        type="text"
                        name="role"
                        value={formData.role}
                        onChange={handleChange}
                        required
                        className="form-control"
                    />
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                    <div className="form-group">
                        <label className="form-label">Min CGPA</label>
                        <input
                            type="number"
                            step="0.01"
                            name="minCgpa"
                            value={formData.minCgpa}
                            onChange={handleChange}
                            className="form-control"
                        />
                    </div>
                    <div className="form-group">
                        <label className="form-label">Max Age</label>
                        <input
                            type="number"
                            name="maxAge"
                            value={formData.maxAge}
                            onChange={handleChange}
                            className="form-control"
                        />
                    </div>
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                    <div className="form-group">
                        <label className="form-label">Gender Preference</label>
                        <select
                            name="genderPreference"
                            value={formData.genderPreference}
                            onChange={handleChange}
                            className="form-control"
                        >
                            <option value="Any">Any</option>
                            <option value="M">Male</option>
                            <option value="F">Female</option>
                        </select>
                    </div>
                    <div className="form-group">
                        <label className="form-label">Max Backlogs</label>
                        <input
                            type="number"
                            name="maxBacklogs"
                            value={formData.maxBacklogs}
                            onChange={handleChange}
                            className="form-control"
                        />
                    </div>
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                    <div className="form-group">
                        <label className="form-label">Required Experience (months)</label>
                        <input
                            type="number"
                            name="requiredExperienceMonths"
                            value={formData.requiredExperienceMonths}
                            onChange={handleChange}
                            className="form-control"
                        />
                    </div>
                    <div className="form-group">
                        <label className="form-label">Year</label>
                        <input
                            type="number"
                            name="year"
                            value={formData.year}
                            onChange={handleChange}
                            required
                            className="form-control"
                        />
                    </div>
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                    <div className="form-group">
                        <label className="form-label">Pre‑placement Talk Date</label>
                        <input
                            type="date"
                            name="pptDate"
                            value={formData.pptDate}
                            onChange={handleChange}
                            className="form-control"
                        />
                    </div>
                    <div className="form-group">
                        <label className="form-label">Placement Date</label>
                        <input
                            type="date"
                            name="placementDate"
                            value={formData.placementDate}
                            onChange={handleChange}
                            className="form-control"
                        />
                    </div>
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                    <div className="form-group">
                        <label className="form-label">Min Package (LPA)</label>
                        <input
                            type="number"
                            step="0.01"
                            name="packageMinLpa"
                            value={formData.packageMinLpa}
                            onChange={handleChange}
                            className="form-control"
                        />
                    </div>
                    <div className="form-group">
                        <label className="form-label">Max Package (LPA)</label>
                        <input
                            type="number"
                            step="0.01"
                            name="packageMaxLpa"
                            value={formData.packageMaxLpa}
                            onChange={handleChange}
                            className="form-control"
                        />
                    </div>
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                    <div className="form-group">
                        <label className="form-label">Number of Rounds</label>
                        <input
                            type="number"
                            name="noOfRounds"
                            value={formData.noOfRounds}
                            onChange={handleChange}
                            className="form-control"
                        />
                    </div>
                    <div className="form-group">
                        <label className="form-label">Number Hired</label>
                        <input
                            type="number"
                            name="noOfPeopleHired"
                            value={formData.noOfPeopleHired}
                            onChange={handleChange}
                            className="form-control"
                        />
                    </div>
                </div>

                <div className="form-group">
                    <label className="form-label">Eligibility Notes</label>
                    <textarea
                        name="eligibilityNotes"
                        value={formData.eligibilityNotes}
                        onChange={handleChange}
                        className="form-control"
                        rows="3"
                    />
                </div>

                <div className="form-group">
                    <label className="form-label">Description</label>
                    <textarea
                        name="description"
                        value={formData.description}
                        onChange={handleChange}
                        className="form-control"
                        rows="3"
                    />
                </div>

                {/* Branches */}
                <div className="form-group">
                    <label className="form-label">Allowed Branches</label>
                    <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
                        <input
                            type="text"
                            placeholder="Search branches..."
                            value={branchSearch}
                            onChange={(e) => setBranchSearch(e.target.value)}
                            className="form-control"
                            style={{ flex: 1 }}
                        />
                        <div style={{ display: 'flex', gap: '5px' }}>
                            <button type="button" onClick={selectAllBranches} className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }}>
                                Select All
                            </button>
                            <button type="button" onClick={clearBranches} className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }}>
                                Clear
                            </button>
                        </div>
                    </div>
                    <div className="checkbox-grid">
                        {filteredBranches.length === 0 ? (
                            <p style={{ color: '#999' }}>No branches available for your college.</p>
                        ) : (
                            filteredBranches.map(b => (
                                <label key={b.id} className="checkbox-label">
                                    <input
                                        type="checkbox"
                                        checked={formData.branchIds.includes(b.id)}
                                        onChange={() => handleBranchToggle(b.id)}
                                    />
                                    {b.name}
                                </label>
                            ))
                        )}
                    </div>
                    <small>Selected: {formData.branchIds.length} / {branchSource.length}</small>
                </div>

                {/* Skills */}
                <div className="form-group">
                    <label className="form-label">Required Skills</label>
                    <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
                        <input
                            type="text"
                            placeholder="Search skills..."
                            value={skillSearch}
                            onChange={(e) => setSkillSearch(e.target.value)}
                            className="form-control"
                            style={{ flex: 1 }}
                        />
                        <div style={{ display: 'flex', gap: '5px' }}>
                            <button type="button" onClick={selectAllSkills} className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }}>
                                Select All
                            </button>
                            <button type="button" onClick={clearSkills} className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }}>
                                Clear
                            </button>
                        </div>
                    </div>
                    <div className="checkbox-grid">
                        {filteredSkills.map(s => (
                            <label key={s.id} className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={formData.skillIds.includes(s.id)}
                                    onChange={() => handleSkillToggle(s.id)}
                                />
                                {s.name} {s.category && `(${s.category})`}
                            </label>
                        ))}
                    </div>
                    <small>Selected: {formData.skillIds.length} / {allSkills.length}</small>
                </div>

                {/* Locations */}
                <div className="form-group">
                    <label className="form-label">Work Locations</label>
                    <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
                        <input
                            type="text"
                            placeholder="Search locations..."
                            value={locationSearch}
                            onChange={(e) => setLocationSearch(e.target.value)}
                            className="form-control"
                            style={{ flex: 1 }}
                        />
                        <div style={{ display: 'flex', gap: '5px' }}>
                            <button type="button" onClick={selectAllLocations} className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }}>
                                Select All
                            </button>
                            <button type="button" onClick={clearLocations} className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }}>
                                Clear
                            </button>
                        </div>
                    </div>
                    <div className="checkbox-grid">
                        {filteredLocations.map(l => (
                            <label key={l.id} className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={formData.locationIds.includes(l.id)}
                                    onChange={() => handleLocationToggle(l.id)}
                                />
                                {l.name}
                            </label>
                        ))}
                    </div>
                    <small>Selected: {formData.locationIds.length} / {allLocations.length}</small>
                </div>

                <div style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end', marginTop: '20px' }}>
                    <button type="submit" className="btn btn-success" disabled={saving}>
                        {saving ? 'Saving...' : (isEditing ? 'Update' : 'Create')}
                    </button>
                    <button type="button" onClick={() => navigate('/admin/drives')} className="btn btn-secondary">
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
};

export default DriveForm;