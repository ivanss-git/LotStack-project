function App() {
  return (
    <div style={{ 
      display: 'flex', 
      flexDirection: 'column', 
      alignItems: 'center', 
      justifyContent: 'center', 
      minHeight: '100%', 
      width: '100%', 
      gap: '40px', 
      padding: '20px 0',
      maxWidth: 'none',
      border: 'none' 
    }}>
      
      {/* Financial Metrics Dashboard Row */}
      <section style={{ display: 'flex', gap: '32px', justifyContent: 'center' }}>
        
        {/* Column 1: Total Capital Invested */}
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '4px', whiteSpace: 'nowrap' }}>
          <div><h2>Total Capital Invested</h2></div>
          <div style={{ fontSize: '18px', fontWeight: 'bold' }}>$15,000.00</div>
        </div>

        {/* Column 2: Total Gross Profit */}
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '4px', whiteSpace: 'nowrap' }}>
          <div><h2>Total Gross Profit</h2></div>
          <div style={{ fontSize: '18px', fontWeight: 'bold' }}>$3,000.00</div>
        </div>

        {/* Column 3: Total Revenue */}
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '4px', whiteSpace: 'nowrap' }}>
          <div><h2>Total Revenue</h2></div>
          <div style={{ fontSize: '18px', fontWeight: 'bold' }}>$18,000.00</div>
        </div>

        {/* Column 4: Average ROI */}
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '4px', whiteSpace: 'nowrap' }}>
          <div><h2>Average ROI</h2></div>
          {/* You can hardcode this, or let React math it dynamically below */}
          <div style={{ fontSize: '18px', fontWeight: 'bold', color: '#4CAF50' }}>20%</div>
        </div>

      </section>

    </div>
  )
}

export default App
