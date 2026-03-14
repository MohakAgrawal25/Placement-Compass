import React, { useState, useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../axiosConfig';
import {
  BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend,
  PieChart, Pie, Cell, LineChart, Line, ResponsiveContainer
} from 'recharts';

const Visualize = () => {
    const navigate = useNavigate();

    // Data states
    const [allDrives, setAllDrives] = useState([]);
    const [colleges, setColleges] = useState([]);
    const [companies, setCompanies] = useState([]);
    const [branches, setBranches] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    // Selection states
    const [chartType, setChartType] = useState('bar');
    const [groupBy, setGroupBy] = useState('college');
    const [metric, setMetric] = useState('avgPackage');
    const [selectedCollege, setSelectedCollege] = useState('all');
    const [selectedCompany, setSelectedCompany] = useState('all');
    const [selectedBranch, setSelectedBranch] = useState('all');
    const [yearRange, setYearRange] = useState({ start: 2020, end: 2025 });

    // Available metrics and groupings
    const metrics = [
        { value: 'avgPackage', label: 'Average Package (LPA)' },
        { value: 'maxPackage', label: 'Max Package (LPA)' },
        { value: 'minPackage', label: 'Min Package (LPA)' },
        { value: 'totalDrives', label: 'Number of Drives' },
        { value: 'totalHired', label: 'Total Hired' },
        { value: 'avgRounds', label: 'Average Rounds' },
    ];

    const groupByOptions = [
        { value: 'college', label: 'College' },
        { value: 'company', label: 'Company' },
        { value: 'branch', label: 'Branch' },
        { value: 'year', label: 'Year' },
    ];

    const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042', '#8884D8', '#82ca9d', '#ffc658', '#8dd1e1'];

    // Fetch all necessary data on mount
    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError('');
            try {
                const [drivesRes, collegesRes, companiesRes, branchesRes] = await Promise.all([
                    axios.get('/drives'),
                    axios.get('/colleges'),
                    axios.get('/companies'),
                    axios.get('/branches')
                ]);

                setAllDrives(Array.isArray(drivesRes.data) ? drivesRes.data : []);
                setColleges(Array.isArray(collegesRes.data) ? collegesRes.data : []);
                setCompanies(Array.isArray(companiesRes.data) ? companiesRes.data : []);
                setBranches(Array.isArray(branchesRes.data) ? branchesRes.data : []);
            } catch (err) {
                console.error('Error fetching data:', err);
                setError('Failed to load data. Please refresh the page.');
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    // Filter drives based on selections
    const filteredDrives = useMemo(() => {
        return allDrives.filter(drive => {
            // Year filter
            if (drive.year < yearRange.start || drive.year > yearRange.end) return false;
            // College filter
            if (selectedCollege !== 'all' && drive.collegeId !== parseInt(selectedCollege)) return false;
            // Company filter
            if (selectedCompany !== 'all' && drive.companyId !== parseInt(selectedCompany)) return false;
            // Branch filter (if drive has branches array)
            if (selectedBranch !== 'all') {
                const branchIds = drive.branches?.map(b => b.id) || [];
                if (!branchIds.includes(parseInt(selectedBranch))) return false;
            }
            return true;
        });
    }, [allDrives, selectedCollege, selectedCompany, selectedBranch, yearRange]);

    // Aggregate data based on groupBy and metric
    const chartData = useMemo(() => {
        if (!filteredDrives.length) return [];

        // Group by selected category
        const groups = {};
        filteredDrives.forEach(drive => {
            let key;
            if (groupBy === 'college') key = drive.collegeName;
            else if (groupBy === 'company') key = drive.companyName;
            else if (groupBy === 'branch') {
                // A drive can have multiple branches; we'll treat each branch separately
                (drive.branches || []).forEach(branch => {
                    const branchKey = branch.name;
                    if (!groups[branchKey]) groups[branchKey] = { count: 0, sumPackage: 0, sumRounds: 0, sumHired: 0, minPackage: Infinity, maxPackage: -Infinity };
                    groups[branchKey].count++;
                    groups[branchKey].sumPackage += (drive.packageMinLpa + drive.packageMaxLpa) / 2; // average of min and max
                    groups[branchKey].sumRounds += drive.noOfRounds || 0;
                    groups[branchKey].sumHired += drive.noOfPeopleHired || 0;
                    groups[branchKey].minPackage = Math.min(groups[branchKey].minPackage, drive.packageMinLpa || Infinity);
                    groups[branchKey].maxPackage = Math.max(groups[branchKey].maxPackage, drive.packageMaxLpa || -Infinity);
                });
                return; // skip the rest for branches
            } else if (groupBy === 'year') key = drive.year.toString();

            // For non-branch grouping
            if (!key) return;
            if (!groups[key]) groups[key] = { count: 0, sumPackage: 0, sumRounds: 0, sumHired: 0, minPackage: Infinity, maxPackage: -Infinity };
            groups[key].count++;
            groups[key].sumPackage += (drive.packageMinLpa + drive.packageMaxLpa) / 2;
            groups[key].sumRounds += drive.noOfRounds || 0;
            groups[key].sumHired += drive.noOfPeopleHired || 0;
            groups[key].minPackage = Math.min(groups[key].minPackage, drive.packageMinLpa || Infinity);
            groups[key].maxPackage = Math.max(groups[key].maxPackage, drive.packageMaxLpa || -Infinity);
        });

        // Convert groups to array and compute metric
        return Object.entries(groups).map(([name, stats]) => {
            let value;
            switch (metric) {
                case 'avgPackage':
                    value = stats.count ? stats.sumPackage / stats.count : 0;
                    break;
                case 'maxPackage':
                    value = stats.maxPackage === -Infinity ? 0 : stats.maxPackage;
                    break;
                case 'minPackage':
                    value = stats.minPackage === Infinity ? 0 : stats.minPackage;
                    break;
                case 'totalDrives':
                    value = stats.count;
                    break;
                case 'totalHired':
                    value = stats.sumHired;
                    break;
                case 'avgRounds':
                    value = stats.count ? stats.sumRounds / stats.count : 0;
                    break;
                default:
                    value = 0;
            }
            return { name, value };
        }).sort((a, b) => b.value - a.value); // sort descending for better display
    }, [filteredDrives, groupBy, metric]);

    // Render chart based on chartType
    const renderChart = () => {
        if (chartData.length === 0) {
            return <p style={{ textAlign: 'center', color: 'var(--gray)' }}>No data available for selected criteria.</p>;
        }

        switch (chartType) {
            case 'pie':
                return (
                    <ResponsiveContainer width="100%" height={400}>
                        <PieChart>
                            <Pie
                                data={chartData}
                                cx="50%"
                                cy="50%"
                                labelLine
                                outerRadius={150}
                                fill="#8884d8"
                                dataKey="value"
                                label={({ name, value }) => `${name}: ${value.toFixed(2)}`}
                            >
                                {chartData.map((entry, index) => (
                                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                                ))}
                            </Pie>
                            <Tooltip />
                        </PieChart>
                    </ResponsiveContainer>
                );
            case 'line':
                return (
                    <ResponsiveContainer width="100%" height={400}>
                        <LineChart data={chartData}>
                            <CartesianGrid strokeDasharray="3 3" />
                            <XAxis dataKey="name" />
                            <YAxis />
                            <Tooltip />
                            <Legend />
                            <Line type="monotone" dataKey="value" stroke="#8884d8" activeDot={{ r: 8 }} />
                        </LineChart>
                    </ResponsiveContainer>
                );
            case 'bar':
            default:
                return (
                    <ResponsiveContainer width="100%" height={400}>
                        <BarChart data={chartData}>
                            <CartesianGrid strokeDasharray="3 3" />
                            <XAxis dataKey="name" />
                            <YAxis />
                            <Tooltip />
                            <Legend />
                            <Bar dataKey="value" fill="#8884d8" />
                        </BarChart>
                    </ResponsiveContainer>
                );
        }
    };

    if (loading) return <div className="container">Loading...</div>;
    if (error) return <div className="container"><p style={{ color: 'var(--danger)' }}>{error}</p></div>;

    return (
        <div className="container">
            <button onClick={() => navigate('/student')} className="btn btn-secondary" style={{ marginBottom: '20px' }}>
                ← Back to Home
            </button>
            <h2>Placement Insights</h2>

            <div style={{
                display: 'flex',
                flexWrap: 'wrap',
                gap: '15px',
                marginBottom: '20px',
                padding: '15px',
                backgroundColor: 'var(--light)',
                borderRadius: 'var(--border-radius)'
            }}>
                {/* Chart Type Selector */}
                <div style={{ display: 'flex', flexDirection: 'column', gap: '5px', minWidth: '150px' }}>
                    <label>Chart Type:</label>
                    <select value={chartType} onChange={(e) => setChartType(e.target.value)} className="form-control">
                        <option value="bar">Bar Chart</option>
                        <option value="pie">Pie Chart</option>
                        <option value="line">Line Chart</option>
                    </select>
                </div>

                {/* Group By */}
                <div style={{ display: 'flex', flexDirection: 'column', gap: '5px', minWidth: '150px' }}>
                    <label>Group By:</label>
                    <select value={groupBy} onChange={(e) => setGroupBy(e.target.value)} className="form-control">
                        {groupByOptions.map(opt => (
                            <option key={opt.value} value={opt.value}>{opt.label}</option>
                        ))}
                    </select>
                </div>

                {/* Metric */}
                <div style={{ display: 'flex', flexDirection: 'column', gap: '5px', minWidth: '150px' }}>
                    <label>Metric:</label>
                    <select value={metric} onChange={(e) => setMetric(e.target.value)} className="form-control">
                        {metrics.map(m => (
                            <option key={m.value} value={m.value}>{m.label}</option>
                        ))}
                    </select>
                </div>

                {/* College Filter */}
                <div style={{ display: 'flex', flexDirection: 'column', gap: '5px', minWidth: '150px' }}>
                    <label>College:</label>
                    <select value={selectedCollege} onChange={(e) => setSelectedCollege(e.target.value)} className="form-control">
                        <option value="all">All Colleges</option>
                        {colleges.map(c => (
                            <option key={c.id} value={c.id}>{c.name}</option>
                        ))}
                    </select>
                </div>

                {/* Company Filter */}
                <div style={{ display: 'flex', flexDirection: 'column', gap: '5px', minWidth: '150px' }}>
                    <label>Company:</label>
                    <select value={selectedCompany} onChange={(e) => setSelectedCompany(e.target.value)} className="form-control">
                        <option value="all">All Companies</option>
                        {companies.map(c => (
                            <option key={c.id} value={c.id}>{c.name}</option>
                        ))}
                    </select>
                </div>

                {/* Branch Filter */}
                <div style={{ display: 'flex', flexDirection: 'column', gap: '5px', minWidth: '150px' }}>
                    <label>Branch:</label>
                    <select value={selectedBranch} onChange={(e) => setSelectedBranch(e.target.value)} className="form-control">
                        <option value="all">All Branches</option>
                        {branches.map(b => (
                            <option key={b.id} value={b.id}>{b.name}</option>
                        ))}
                    </select>
                </div>

                {/* Year Range */}
                <div style={{ display: 'flex', flexDirection: 'column', gap: '5px', minWidth: '150px' }}>
                    <label>Year Range:</label>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '5px' }}>
                        <input
                            type="number"
                            value={yearRange.start}
                            onChange={(e) => setYearRange({ ...yearRange, start: parseInt(e.target.value) })}
                            className="form-control"
                            style={{ width: '80px' }}
                            placeholder="Start"
                        />
                        <span> – </span>
                        <input
                            type="number"
                            value={yearRange.end}
                            onChange={(e) => setYearRange({ ...yearRange, end: parseInt(e.target.value) })}
                            className="form-control"
                            style={{ width: '80px' }}
                            placeholder="End"
                        />
                    </div>
                </div>
            </div>

            <div className="card" style={{ marginTop: '30px', padding: '20px' }}>
                {renderChart()}
            </div>
        </div>
    );
};

export default Visualize;