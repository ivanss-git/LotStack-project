interface MetricCardProps {
  icon?: string;
  title: string;
  value: string | number;
  iconBgColor?: string;
  isPositive?: boolean
}

function App() {

  const metrics: MetricCardProps[] = [
    { title: 'Total Capital Invested', value: '$15,000.00' },
    { title: 'Total Gross Profit', value: '$3,000.00' },
    { title: 'Total Revenue', value: '$18,000.00' },
    { title: 'Average ROI', value: '20.00%', isPositive: true },
  ];

  return (
    <div style={{ 
      display: 'flex', 
      flexDirection: 'column', 
      alignItems: 'center', 
      justifyContent: 'center', 
      minHeight: '100%', 
      width: '100%', 
      gap: '40px', 
      padding: '20px',
      maxWidth: 'none',
      border: '2px solid #4c4949',
      backgroundColor: '#f8fafc',
      boxSizing: 'border-box'
    }}>
      {/* Financial Metrics Dashboard Row */}
      <section style={{
        display: 'flex',
        flexDirection: 'row',
        width: '100%',
        gap: '24px',
        justifyContent: 'center'
      }}>

        {/* Shared Styling structure via a reuseable style object */}
        {/* Column 1: Total Capital Invested */}
        <div style={{
          flex: 1,                       //ensures box grows evenly and shares space
          display: 'flex',               // left aligned text
          flexDirection: 'column',
          alignItems: 'flex-start',
          gap: '4px', 
          whiteSpace: 'nowrap',
          backgroundColor: '#ffffff',
          border: '1px solid #e2e8f0', // Clean card border
          borderRadius: '12px',          // Rounded container box corners
          padding: '24px',               // Breathing space inside individual boxes
          boxShadow: '0 1px 3px rgba(0, 0, 0, 0.05)'
        }}>
          <div style={{ margin: 0 }}><h2 style={{ fontSize: '14px', color: '#64748b', margin: 0, fontWeight: 500}}> Total Capital Invested</h2></div>
          <div style={{ fontSize: '28px', fontWeight: 'bold', color: '#0f172a', marginTop: '4px' }}>$15,000.00</div> 
        </div>
        
        {/* Column 2: Total Gross Profit */}
        <div style={{ 
          flex: 1,
          display: 'flex', 
          flexDirection: 'column', 
          alignItems: 'center', 
          gap: '4px', 
          whiteSpace: 'nowrap',
          backgroundColor: '#ffffff',
          border: '1px solid #e2e8f0',
          borderRadius: '12px',
          padding: '24px',
          boxShadow: '0 1px 3px rgba(0, 0, 0, 0.05)' 
          }}>
          <div><h2 style={{ fontSize: '14px', color: '#64748b', margin: 0, fontWeight: 500 }}>Total Gross Profit</h2></div>
          <div style={{ fontSize: '28px', fontWeight: 'bold', color: '#0f172a', marginTop: '4px' }}>$15,000.00</div>
        </div>

        {/* Column 3: Total Revenue */}
        <div style={{ 
          flex: 1,
          display: 'flex', 
          flexDirection: 'column', 
          alignItems: 'center', 
          gap: '4px', 
          whiteSpace: 'nowrap',
          backgroundColor: '#ffffff',
          border: '1px solid #e2e8f0',
          borderRadius: '12px',
          padding: '24px',
          boxShadow: '0 1px 3px rgba(0, 0, 0, 0.05)'
          }}>
          <div><h2 style={{ fontSize: '14px', color: '#64748b', margin: 0, fontWeight: 500}}>Total Gross Profit</h2></div>
          <div style={{ fontSize: '28px', fontWeight: 'bold', color: '#0f172a', marginTop: '4px' }}>$3,000.00</div>
        </div>

        {/* Column 4: Total ROI */}
        <div style={{ 
          flex: 1,
          display: 'flex', 
          flexDirection: 'column', 
          alignItems: 'center', 
          gap: '4px',
          whiteSpace: 'nowrap',
          backgroundColor: '#ffffff',
          border: '1px solid #e2e8f0',
          borderRadius: '12px',
          padding: '24px',
          boxShadow: '0 1px 3px rgba(0, 0, 0, 0.05)'
          }}>
          <div><h2 style={{ fontSize: '14px', color: '#64748b', margin: 0, fontWeight: 500 }}>Total ROI</h2></div>
          <div style={{ fontSize: '28px', fontWeight: 'bold', color: '#4CAF50', marginTop: '4px' }}>20.00%</div>
        </div>
      
      </section>

    </div>
  );
}

export default App
