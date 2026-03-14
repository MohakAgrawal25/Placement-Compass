import React, { useState, useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';

const StudentFilter = () => {
    const navigate = useNavigate();

    // Data states
    const [allDrives, setAllDrives] = useState([]);
    const [filteredDrives, setFilteredDrives] = useState([]);
    const [colleges, setColleges] = useState([]);
    const [companies, setCompanies] = useState([]);
    const [allBranches, setAllBranches] = useState([]);
    const [allLocations, setAllLocations] = useState([]);

    // Filter states
    const [selectedCollegeIds, setSelectedCollegeIds] = useState([]);
    const [selectedCompanyIds, setSelectedCompanyIds] = useState([]);
    const [selectedBranchIds, setSelectedBranchIds] = useState([]);
    const [selectedLocationIds, setSelectedLocationIds] = useState([]);
    const [selectedGenders, setSelectedGenders] = useState([]); // 'M', 'F', 'Any'
    const [minRounds, setMinRounds] = useState('');

    // Search inputs for filter options
    const [collegeSearch, setCollegeSearch] = useState('');
    const [companySearch, setCompanySearch] = useState('');
    const [branchSearch, setBranchSearch] = useState('');
    const [locationSearch, setLocationSearch] = useState('');

    // Sort states
    const [sortField, setSortField] = useState('year'); // 'package', 'placementDate', 'year', 'rounds'
    const [sortOrder, setSortOrder] = useState('desc'); // 'asc' or 'desc'

    // UI states
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [filterPanelOpen, setFilterPanelOpen] = useState(false);

    // Modal state
    const [selectedDrive, setSelectedDrive] = useState(null);

    // Fetch all data on mount
    useEffect(() => {
        const fetchAllData = async () => {
            setLoading(true);
            setError('');
            try {
                const [drivesRes, collegesRes, companiesRes, branchesRes, locationsRes] = await Promise.all([
                    axios.get('/drives'),
                    axios.get('/colleges'),
                    axios.get('/companies'),
                    axios.get('/branches'),
                    axios.get('/locations')
                ]);

                setAllDrives(drivesRes.data);
                setFilteredDrives(drivesRes.data);
                setColleges(Array.isArray(collegesRes.data) ? collegesRes.data : []);
                setCompanies(Array.isArray(companiesRes.data) ? companiesRes.data : []);
                setAllBranches(Array.isArray(branchesRes.data) ? branchesRes.data : []);
                setAllLocations(Array.isArray(locationsRes.data) ? locationsRes.data : []);
            } catch (err) {
                console.error('Error fetching data:', err);
                setError('Failed to load data. Please refresh the page.');
            } finally {
                setLoading(false);
            }
        };
        fetchAllData();
    }, []);

    // Filter and sort drives whenever filter/sort criteria change
    useEffect(() => {
        let result = [...allDrives];

        // Apply filters
        if (selectedCollegeIds.length > 0) {
            result = result.filter(drive => selectedCollegeIds.includes(drive.collegeId));
        }
        if (selectedCompanyIds.length > 0) {
            result = result.filter(drive => selectedCompanyIds.includes(drive.companyId));
        }
        if (selectedBranchIds.length > 0) {
            result = result.filter(drive =>
                drive.branches?.some(b => selectedBranchIds.includes(b.id))
            );
        }
        if (selectedLocationIds.length > 0) {
            result = result.filter(drive =>
                drive.locations?.some(l => selectedLocationIds.includes(l.id))
            );
        }
        if (selectedGenders.length > 0) {
            result = result.filter(drive =>
                selectedGenders.includes(drive.genderPreference)
            );
        }
        if (minRounds) {
            const min = parseInt(minRounds);
            if (!isNaN(min)) {
                result = result.filter(drive => (drive.noOfRounds || 0) >= min);
            }
        }

        // Apply sorting
        result.sort((a, b) => {
            let aVal, bVal;
            switch (sortField) {
                case 'package':
                    aVal = a.packageMinLpa || 0;
                    bVal = b.packageMinLpa || 0;
                    break;
                case 'placementDate':
                    aVal = a.placementDate ? new Date(a.placementDate) : new Date(0);
                    bVal = b.placementDate ? new Date(b.placementDate) : new Date(0);
                    break;
                case 'year':
                    aVal = a.year || 0;
                    bVal = b.year || 0;
                    break;
                case 'rounds':
                    aVal = a.noOfRounds || 0;
                    bVal = b.noOfRounds || 0;
                    break;
                default:
                    aVal = a.year || 0;
                    bVal = b.year || 0;
            }
            if (sortOrder === 'asc') {
                return aVal > bVal ? 1 : -1;
            } else {
                return aVal < bVal ? 1 : -1;
            }
        });

        setFilteredDrives(result);
    }, [allDrives, selectedCollegeIds, selectedCompanyIds, selectedBranchIds, selectedLocationIds, selectedGenders, minRounds, sortField, sortOrder]);

    // Handlers for multi‑select checkboxes
    const toggleSelection = (setter, value) => {
        setter(prev =>
            prev.includes(value)
                ? prev.filter(v => v !== value)
                : [...prev, value]
        );
    };

    // Select all / clear helpers
    const selectAllColleges = () => {
        const allIds = filteredColleges.map(c => c.id);
        setSelectedCollegeIds(allIds);
    };
    const clearColleges = () => setSelectedCollegeIds([]);

    const selectAllCompanies = () => {
        const allIds = filteredCompanies.map(c => c.id);
        setSelectedCompanyIds(allIds);
    };
    const clearCompanies = () => setSelectedCompanyIds([]);

    const selectAllBranches = () => {
        const allIds = filteredBranches.map(b => b.id);
        setSelectedBranchIds(allIds);
    };
    const clearBranches = () => setSelectedBranchIds([]);

    const selectAllLocations = () => {
        const allIds = filteredLocations.map(l => l.id);
        setSelectedLocationIds(allIds);
    };
    const clearLocations = () => setSelectedLocationIds([]);

    const selectAllGenders = () => setSelectedGenders(['M', 'F', 'Any']);
    const clearGenders = () => setSelectedGenders([]);

    // Filtered lists for search
    const filteredColleges = useMemo(() => {
        return colleges.filter(c =>
            c.name.toLowerCase().includes(collegeSearch.toLowerCase())
        );
    }, [colleges, collegeSearch]);

    const filteredCompanies = useMemo(() => {
        return companies.filter(c =>
            c.name.toLowerCase().includes(companySearch.toLowerCase())
        );
    }, [companies, companySearch]);

    const filteredBranches = useMemo(() => {
        return allBranches.filter(b =>
            b.name.toLowerCase().includes(branchSearch.toLowerCase())
        );
    }, [allBranches, branchSearch]);

    const filteredLocations = useMemo(() => {
        return allLocations.filter(l =>
            l.name.toLowerCase().includes(locationSearch.toLowerCase())
        );
    }, [allLocations, locationSearch]);

    const formatDate = (dateStr) => {
        if (!dateStr) return '-';
        return new Date(dateStr).toLocaleDateString();
    };

    const openDetails = (drive) => setSelectedDrive(drive);
    const closeDetails = () => setSelectedDrive(null);

    if (loading) return <div className="container">Loading placement drives...</div>;
    if (error) return <div className="container"><p style={{ color: 'var(--danger)' }}>{error}</p></div>;

    return (
        <div className="container">
            <button onClick={() => navigate('/student')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Home
            </button>
            <h2>Placement Drives</h2>

            {/* Filter and Sort Controls */}
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
                <button
                    className="btn btn-primary"
                    onClick={() => setFilterPanelOpen(!filterPanelOpen)}
                >
                    {filterPanelOpen ? 'Hide Filters' : 'Show Filters'}
                </button>

                <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                    <label>Sort by:</label>
                    <select
                        value={sortField}
                        onChange={(e) => setSortField(e.target.value)}
                        className="form-control"
                        style={{ width: 'auto' }}
                    >
                        <option value="year">Year</option>
                        <option value="package">Package (Min)</option>
                        <option value="placementDate">Placement Date</option>
                        <option value="rounds">Number of Rounds</option>
                    </select>
                    <button
                        className="btn btn-secondary"
                        onClick={() => setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc')}
                    >
                        {sortOrder === 'asc' ? '⬆ Asc' : '⬇ Desc'}
                    </button>
                </div>
            </div>

            {/* Filter Panel */}
            {filterPanelOpen && (
                <div className="filter-panel">
                    {/* Colleges */}
                    <div className="filter-group">
                        <h4>College</h4>
                        <div style={{ display: 'flex', gap: '5px', marginBottom: '5px' }}>
                            <button type="button" className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }} onClick={selectAllColleges}>Select All</button>
                            <button type="button" className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }} onClick={clearColleges}>Clear</button>
                        </div>
                        <input
                            type="text"
                            placeholder="Search colleges..."
                            value={collegeSearch}
                            onChange={(e) => setCollegeSearch(e.target.value)}
                            className="form-control"
                        />
                        <div className="checkbox-grid">
                            {filteredColleges.map(college => (
                                <label key={college.id} className="checkbox-label">
                                    <input
                                        type="checkbox"
                                        checked={selectedCollegeIds.includes(college.id)}
                                        onChange={() => toggleSelection(setSelectedCollegeIds, college.id)}
                                    />
                                    {college.name}
                                </label>
                            ))}
                        </div>
                    </div>

                    {/* Companies */}
                    <div className="filter-group">
                        <h4>Company</h4>
                        <div style={{ display: 'flex', gap: '5px', marginBottom: '5px' }}>
                            <button type="button" className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }} onClick={selectAllCompanies}>Select All</button>
                            <button type="button" className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }} onClick={clearCompanies}>Clear</button>
                        </div>
                        <input
                            type="text"
                            placeholder="Search companies..."
                            value={companySearch}
                            onChange={(e) => setCompanySearch(e.target.value)}
                            className="form-control"
                        />
                        <div className="checkbox-grid">
                            {filteredCompanies.map(company => (
                                <label key={company.id} className="checkbox-label">
                                    <input
                                        type="checkbox"
                                        checked={selectedCompanyIds.includes(company.id)}
                                        onChange={() => toggleSelection(setSelectedCompanyIds, company.id)}
                                    />
                                    {company.name}
                                </label>
                            ))}
                        </div>
                    </div>

                    {/* Branches */}
                    <div className="filter-group">
                        <h4>Branch</h4>
                        <div style={{ display: 'flex', gap: '5px', marginBottom: '5px' }}>
                            <button type="button" className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }} onClick={selectAllBranches}>Select All</button>
                            <button type="button" className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }} onClick={clearBranches}>Clear</button>
                        </div>
                        <input
                            type="text"
                            placeholder="Search branches..."
                            value={branchSearch}
                            onChange={(e) => setBranchSearch(e.target.value)}
                            className="form-control"
                        />
                        <div className="checkbox-grid">
                            {filteredBranches.map(branch => (
                                <label key={branch.id} className="checkbox-label">
                                    <input
                                        type="checkbox"
                                        checked={selectedBranchIds.includes(branch.id)}
                                        onChange={() => toggleSelection(setSelectedBranchIds, branch.id)}
                                    />
                                    {branch.name}
                                </label>
                            ))}
                        </div>
                    </div>

                    {/* Locations */}
                    <div className="filter-group">
                        <h4>Location</h4>
                        <div style={{ display: 'flex', gap: '5px', marginBottom: '5px' }}>
                            <button type="button" className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }} onClick={selectAllLocations}>Select All</button>
                            <button type="button" className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }} onClick={clearLocations}>Clear</button>
                        </div>
                        <input
                            type="text"
                            placeholder="Search locations..."
                            value={locationSearch}
                            onChange={(e) => setLocationSearch(e.target.value)}
                            className="form-control"
                        />
                        <div className="checkbox-grid">
                            {filteredLocations.map(location => (
                                <label key={location.id} className="checkbox-label">
                                    <input
                                        type="checkbox"
                                        checked={selectedLocationIds.includes(location.id)}
                                        onChange={() => toggleSelection(setSelectedLocationIds, location.id)}
                                    />
                                    {location.name}
                                </label>
                            ))}
                        </div>
                    </div>

                    {/* Gender Preference */}
                    <div className="filter-group">
                        <h4>Gender Preference</h4>
                        <div style={{ display: 'flex', gap: '5px', marginBottom: '5px' }}>
                            <button type="button" className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }} onClick={selectAllGenders}>Select All</button>
                            <button type="button" className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.85rem' }} onClick={clearGenders}>Clear</button>
                        </div>
                        <div className="checkbox-grid">
                            {['M', 'F', 'Any'].map(gender => (
                                <label key={gender} className="checkbox-label">
                                    <input
                                        type="checkbox"
                                        checked={selectedGenders.includes(gender)}
                                        onChange={() => toggleSelection(setSelectedGenders, gender)}
                                    />
                                    {gender === 'M' ? 'Male' : gender === 'F' ? 'Female' : 'Any'}
                                </label>
                            ))}
                        </div>
                    </div>

                    {/* Minimum Rounds */}
                    <div className="filter-group">
                        <h4>Minimum Rounds</h4>
                        <input
                            type="number"
                            min="0"
                            value={minRounds}
                            onChange={(e) => setMinRounds(e.target.value)}
                            className="form-control"
                            placeholder="e.g., 2"
                        />
                    </div>
                </div>
            )}

            {/* Results Count */}
            <p>Found {filteredDrives.length} drives</p>

            {/* Drives Table */}
            <div style={{ marginTop: '20px' }}>
                <table className="table">
                    <thead>
                        <tr>
                            <th>College</th>
                            <th>Company</th>
                            <th>Role</th>
                            <th>Year</th>
                            <th>Placement Date</th>
                            <th>Package (LPA)(Min-Max)</th>
                            <th>Branches</th>
                            <th>Locations</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredDrives.length === 0 ? (
                            <tr>
                                <td colSpan="9" style={{ textAlign: 'center', padding: '20px', color: 'var(--gray)' }}>
                                    No drives match your criteria
                                </td>
                            </tr>
                        ) : (
                            filteredDrives.map(drive => (
                                <tr key={drive.id}>
                                    <td>{drive.collegeName}</td>
                                    <td>{drive.companyName}</td>
                                    <td>{drive.role}</td>
                                    <td>{drive.year}</td>
                                    <td>{formatDate(drive.placementDate)}</td>
                                    <td>
                                        {drive.packageMinLpa} - {drive.packageMaxLpa}
                                    </td>
                                    <td>
                                        {drive.branches?.map(b => b.name).join(', ')}
                                    </td>
                                    <td>
                                        {drive.locations?.map(l => l.name).join(', ')}
                                    </td>
                                    <td>
                                        <button
                                            onClick={() => openDetails(drive)}
                                            style={{
                                                padding: '4px 8px',
                                                backgroundColor: '#17a2b8',
                                                color: '#fff',
                                                border: 'none',
                                                borderRadius: '4px',
                                                cursor: 'pointer',
                                                fontSize: '0.85rem'
                                            }}
                                        >
                                            View Details
                                        </button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>

            {/* Details Modal */}
            {selectedDrive && (
                <div className="modal-overlay" onClick={closeDetails}>
                    <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                        <h3>Drive Details</h3>
                        <button className="modal-close" onClick={closeDetails}>×</button>
                        <div className="details-grid">
                            <div><strong>College:</strong> {selectedDrive.collegeName}</div>
                            <div><strong>Company:</strong> {selectedDrive.companyName}</div>
                            <div><strong>Role:</strong> {selectedDrive.role}</div>
                            <div><strong>Year:</strong> {selectedDrive.year}</div>
                            <div><strong>Placement Date:</strong> {formatDate(selectedDrive.placementDate)}</div>
                            <div><strong>Pre‑placement Talk Date:</strong> {formatDate(selectedDrive.pptDate)}</div>
                            <div><strong>Min CGPA:</strong> {selectedDrive.minCgpa || '-'}</div>
                            <div><strong>Max Age:</strong> {selectedDrive.maxAge || '-'}</div>
                            <div><strong>Max Backlogs:</strong> {selectedDrive.maxBacklogs || '-'}</div>
                            <div><strong>Required Experience (months):</strong> {selectedDrive.requiredExperienceMonths || 0}</div>
                            <div><strong>Package (LPA)(Min-Max):</strong> {selectedDrive.packageMinLpa} - {selectedDrive.packageMaxLpa}</div>
                            <div><strong>Number of Rounds:</strong> {selectedDrive.noOfRounds || '-'}</div>
                            <div><strong>Number Hired:</strong> {selectedDrive.noOfPeopleHired || '-'}</div>
                            <div><strong>Gender Preference:</strong> {selectedDrive.genderPreference || 'Any'}</div>
                            <div><strong>Eligibility Notes:</strong> {selectedDrive.eligibilityNotes || '-'}</div>
                            <div><strong>Description:</strong> {selectedDrive.description || '-'}</div>
                            <div><strong>Branches:</strong> {selectedDrive.branches?.map(b => b.name).join(', ') || '-'}</div>
                            <div><strong>Skills:</strong> {selectedDrive.skills?.map(s => s.name).join(', ') || '-'}</div>
                            <div><strong>Locations:</strong> {selectedDrive.locations?.map(l => l.name).join(', ') || '-'}</div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default StudentFilter;