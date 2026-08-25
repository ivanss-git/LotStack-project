import { useState } from 'react';
import './App.css';

type ExpenseCategory =
  | 'REPAIR'
  | 'TRANSPORT'
  | 'AUCTION_FEE'
  | 'PAPERWORK'
  | 'OTHER';

interface Expense {
  id: number;
  description: string;
  category: ExpenseCategory;
  amount: number;
}

interface Vehicle {
  id: number;
  year: number;
  make: string;
  model: string;
  vin: string;
  trim: string;
  mileage: number | null;

  thumbnailUrl: string;
  imageUrls: string[];

  purchasePrice: number;
  soldPrice: number | null;
  expenses: Expense[];

  status: 'Sold' | 'Under Repair' | 'Available' | 'Presold';

  lotNumber: string;
  originalListingUrl: string;
  notes?: string;
}

interface MetricCardProps {
  title: string;
  value: string;
  isPositive?: boolean;
}

const vehicles: Vehicle[] = [
  {
    id: 1,
    year: 2018,
    make: 'GMC', model: 'Sierra K3500HD', trim: 'SLE',
    vin: '1GD42VCY3JF249235', mileage: null,
    thumbnailUrl: '/images/car-icon.jpg', imageUrls: ['/images/car-icon.jpg'],
    purchasePrice: 8500, soldPrice: 23500, status: 'Sold',
    expenses: [
      { id: 101, description: 'Door', category: 'REPAIR', amount: 450 },
      { id: 102, description: 'Side step', category: 'REPAIR', amount: 185 },
      { id: 103, description: 'Passenger-side fender', category: 'REPAIR', amount: 475 },
      { id: 104, description: 'Rocker panel', category: 'REPAIR', amount: 300 },
      { id: 105, description: 'Labor', category: 'REPAIR', amount: 800 },
      { id: 106, description: 'Environmental fee', category: 'AUCTION_FEE', amount: 15 },
      { id: 107, description: 'Internet bid fee', category: 'AUCTION_FEE', amount: 140 },
      { id: 108, description: 'Gate fee', category: 'AUCTION_FEE', amount: 95 },
      { id: 109, description: 'Title pickup fee', category: 'PAPERWORK', amount: 20 },
      { id: 110, description: 'Buyer fee', category: 'AUCTION_FEE', amount: 1275 },
      { id: 111, description: 'Sales tax', category: 'PAPERWORK', amount: 125.85 },
    ],
    lotNumber: 'N/A', originalListingUrl: 'https://bidmotors.bg/en/gmc-sierra-k3500-2018-1gd42vcy3jf249235',
    notes: 'Replacement panels matched the vehicle color, so paint was not needed.',
  },
  {
    id: 2, year: 2018, make: 'GMC', model: 'Sierra K1500', trim: 'Denali',
    vin: '3GTU2PEC8JG526641', mileage: null,
    thumbnailUrl: '/images/pickups/2018-denali-black.jpeg', imageUrls: ['/images/pickups/2018-denali-black.jpeg'],
    purchasePrice: 14800, soldPrice: null, status: 'Under Repair',
    expenses: [
      { id: 201, description: 'Front-end suspension kit', category: 'REPAIR', amount: 750 },
      { id: 202, description: 'Labor', category: 'REPAIR', amount: 300 },
      { id: 203, description: 'Alignment', category: 'REPAIR', amount: 100 },
      { id: 204, description: 'Cleaning', category: 'REPAIR', amount: 180 },
      { id: 205, description: 'Battery', category: 'REPAIR', amount: 280 },
      { id: 206, description: 'Virtual bid fee', category: 'AUCTION_FEE', amount: 149 },
      { id: 207, description: 'Gate fee', category: 'AUCTION_FEE', amount: 79 },
      { id: 208, description: 'Title mailing', category: 'PAPERWORK', amount: 5 },
      { id: 209, description: 'FedEx mailing', category: 'PAPERWORK', amount: 5 },
      { id: 210, description: 'Storage', category: 'OTHER', amount: 25 },
      { id: 211, description: 'Buyer fee', category: 'AUCTION_FEE', amount: 850 },
    ],
    lotNumber: '48055846', originalListingUrl: 'https://carfast.express/en/auction/lots/48055846-gmc-sierra-2018-vin-3gtu2pec8jg526641',
    notes: 'Currently undergoing repairs.',
  },
  {
    id: 3, year: 2018, make: 'Chevrolet', model: 'Silverado 1500', trim: 'High Country',
    vin: 'Pending verification', mileage: null,
    thumbnailUrl: '/images/pickups/2018-silverado-high-country.jpeg', imageUrls: ['/images/pickups/2018-silverado-high-country.jpeg'],
    purchasePrice: 12500, soldPrice: 22000, status: 'Sold',
    expenses: [
      { id: 301, description: 'Front-end suspension kit', category: 'REPAIR', amount: 700 },
      { id: 302, description: 'Labor', category: 'REPAIR', amount: 325 },
      { id: 303, description: 'Alignment', category: 'REPAIR', amount: 100 },
      { id: 304, description: 'Cleaning', category: 'REPAIR', amount: 180 },
      { id: 305, description: 'Optima YellowTop 94R/H7 battery', category: 'REPAIR', amount: 379.99 },
      { id: 306, description: 'Steering-wheel airbag', category: 'REPAIR', amount: 1205.91 },
      { id: 307, description: 'Environmental fee', category: 'AUCTION_FEE', amount: 15 },
      { id: 308, description: 'Internet bid fee', category: 'AUCTION_FEE', amount: 140 },
      { id: 309, description: 'Gate fee', category: 'AUCTION_FEE', amount: 95 },
      { id: 310, description: 'Title pickup fee', category: 'PAPERWORK', amount: 20 },
      { id: 311, description: 'Buyer fee', category: 'AUCTION_FEE', amount: 1300 },
      { id: 312, description: 'Sales tax', category: 'PAPERWORK', amount: 156 },
      { id: 313, description: 'Additional auction fees', category: 'AUCTION_FEE', amount: 1070 },
    ],
    lotNumber: 'N/A', originalListingUrl: '',
    notes: 'VIN pending verification. The listed fees total $2,796. Repair total corrected to $2,890.90.',
  },
  {
    id: 4, year: 1997, make: 'Chevrolet', model: 'C1500', trim: 'Base',
    vin: 'Pending verification', mileage: null,
    thumbnailUrl: '/images/pickups/1997-chevrolet-c1500.jpeg', imageUrls: ['/images/pickups/1997-chevrolet-c1500.jpeg'],
    purchasePrice: 1500, soldPrice: 4500, status: 'Sold',
    expenses: [{ id: 401, description: 'Repairs', category: 'REPAIR', amount: 1525 }],
    lotNumber: 'N/A', originalListingUrl: '',
  },
  {
    id: 5, year: 2009, make: 'Honda', model: 'Civic', trim: 'GX',
    vin: '1HGFA46559L001210', mileage: null,
    thumbnailUrl: '/images/sedans/2009-civic-gx.jpeg', imageUrls: ['/images/sedans/2009-civic-gx.jpeg'],
    purchasePrice: 3700, soldPrice: null, status: 'Available',
    expenses: [
      { id: 501, description: 'Battery', category: 'REPAIR', amount: 160 },
      { id: 502, description: 'Hubcaps', category: 'REPAIR', amount: 60 },
      { id: 503, description: 'Interior/exterior detail', category: 'REPAIR', amount: 150 },
      { id: 504, description: 'Buyer fee', category: 'AUCTION_FEE', amount: 300 },
    ],
    lotNumber: '229091', originalListingUrl: 'https://www.lso.cc/auction/7729/item/2009-honda-civic-gx-cng-229091/',
  },
  {
    id: 6, year: 2017, make: 'Volkswagen', model: 'Jetta', trim: 'S',
    vin: '3VW2B7AJ3HM250759', mileage: null,
    thumbnailUrl: '/images/car-icon.jpg', imageUrls: ['/images/car-icon.jpg'],
    purchasePrice: 4800, soldPrice: 6500, status: 'Sold', expenses: [],
    lotNumber: 'N/A', originalListingUrl: '', notes: 'White vehicle; quick flip with no repairs or fees.',
  },
  {
    id: 7, year: 2017, make: 'Volkswagen', model: 'Jetta', trim: 'S',
    vin: '3VW2B7AJ0HM303725', mileage: null,
    thumbnailUrl: '/images/car-icon.jpg', imageUrls: ['/images/car-icon.jpg'],
    purchasePrice: 6500, soldPrice: 12500, status: 'Sold',
    expenses: [
      { id: 701, description: 'Battery', category: 'REPAIR', amount: 150 },
      { id: 702, description: 'Fuel', category: 'TRANSPORT', amount: 50 },
      { id: 703, description: 'Temporary tag', category: 'PAPERWORK', amount: 40 },
      { id: 704, description: 'Temporary insurance', category: 'OTHER', amount: 150 },
      { id: 705, description: 'Tolls', category: 'TRANSPORT', amount: 45 },
      { id: 706, description: 'Customs broker fee', category: 'PAPERWORK', amount: 1100 },
      { id: 707, description: 'Mexico VAT (16%)', category: 'PAPERWORK', amount: 1060 },
      { id: 708, description: 'Customs processing fee', category: 'PAPERWORK', amount: 80 },
      { id: 709, description: 'Broker processing fee', category: 'PAPERWORK', amount: 500 },
    ],
    lotNumber: 'N/A', originalListingUrl: '', notes: 'Black vehicle.',
  },
  {
    id: 8, year: 2015, make: 'Chevrolet', model: 'Equinox', trim: 'LT',
    vin: '1GNALBEK9FZ119453', mileage: null,
    thumbnailUrl: '/images/suvs/2015-chevy-equinox.jpeg', imageUrls: ['/images/suvs/2015-chevy-equinox.jpeg'],
    purchasePrice: 325, soldPrice: 800, status: 'Sold',
    expenses: [{ id: 801, description: 'Pickup/transport', category: 'TRANSPORT', amount: 150 }],
    lotNumber: 'N/A', originalListingUrl: 'https://bidmotors.bg/en/chevrolet-equinox-lt-2015-1gnalbek9fz119453',
    notes: 'Engine was used for another SUV, then the remaining vehicle was sold.',
  },
  {
    id: 9, year: 2007, make: 'CM', model: '12', trim: 'N/A',
    vin: '5VNBU16257T056045', mileage: null,
    thumbnailUrl: '/images/car-icon.jpg', imageUrls: ['/images/car-icon.jpg'],
    purchasePrice: 1500, soldPrice: 4000, status: 'Sold',
    expenses: [
      { id: 901, description: 'Full axle leaf springs', category: 'REPAIR', amount: 400 },
      { id: 902, description: 'Tires', category: 'REPAIR', amount: 300 },
      { id: 903, description: 'Fees', category: 'OTHER', amount: 98 },
    ],
    lotNumber: 'N/A', originalListingUrl: '',
  },
  {
    id: 10, year: 2015, make: 'Chevrolet', model: 'Silverado', trim: 'LT',
    vin: '1GCRCREC3FZ306989', mileage: null,
    thumbnailUrl: '/images/pickups/2015-silverado-red.jpeg', imageUrls: ['/images/pickups/2015-silverado-red.jpeg'],
    purchasePrice: 7500, soldPrice: 16500, status: 'Presold',
    expenses: [
      { id: 1001, description: 'Door', category: 'REPAIR', amount: 600 },
      { id: 1002, description: 'Bed', category: 'REPAIR', amount: 720 },
      { id: 1003, description: 'Bed and door paint', category: 'REPAIR', amount: 800 },
      { id: 1004, description: 'Starter', category: 'REPAIR', amount: 120 },
      { id: 1005, description: 'Cabin work', category: 'REPAIR', amount: 800 },
      { id: 1006, description: 'Cleaning', category: 'REPAIR', amount: 200 },
      { id: 1007, description: 'Tow', category: 'TRANSPORT', amount: 300 },
    ],
    lotNumber: 'N/A', originalListingUrl: '',
    notes: 'Red vehicle. Presold for $16,500 to the same client who purchased the Sierra 3500HD; cosmetic repairs remain.',
  },
];

function calculateRepairCost(vehicle: Vehicle): number {
  return vehicle.expenses
    .filter((expense) => expense.category === 'REPAIR')
    .reduce((total, expense) => total + expense.amount, 0);
}

function calculateAllExpenses(vehicle: Vehicle): number {
  return vehicle.expenses.reduce(
    (total, expense) => total + expense.amount,
    0
  );
}

function calculateTotalInvested(vehicle: Vehicle): number {
  return vehicle.purchasePrice + calculateAllExpenses(vehicle);
}

function calculateProfit(vehicle: Vehicle): number {
  if (vehicle.soldPrice === null) return 0;
  return vehicle.soldPrice - calculateTotalInvested(vehicle);
}

function calculateRoi(vehicle: Vehicle): number | null {
  if (vehicle.soldPrice === null) return null;
  const totalInvested = calculateTotalInvested(vehicle);

  if (totalInvested === 0) {
    return null;
  }

  return (calculateProfit(vehicle) / totalInvested) * 100;
}

function formatCurrency(value: number): string {
  return value.toLocaleString('en-US', {
    style: 'currency',
    currency: 'USD',
    maximumFractionDigits: 0,
  });
}

function formatSalePrice(vehicle: Vehicle): string {
  return vehicle.soldPrice === null
    ? 'Pending'
    : formatCurrency(vehicle.soldPrice);
}

function handleImageError(
  event: React.SyntheticEvent<HTMLImageElement>
) {
  event.currentTarget.onerror = null;
  event.currentTarget.src = '/images/car-icon.jpg';
}

function getStatusStyle(status: Vehicle['status']) {
  const colors = {
    Sold: { backgroundColor: '#dcfce7', color: '#166534' },
    Presold: { backgroundColor: '#fef3c7', color: '#92400e' },
    'Under Repair': { backgroundColor: '#ffedd5', color: '#9a3412' },
    Available: { backgroundColor: '#fee2e2', color: '#b91c1c' },
  };

  return colors[status];
}

function MetricCard({
  title,
  value,
  isPositive = false,
}: MetricCardProps) {
  return (
    <article className="metric-card">
      <span>{title}</span>

      <strong className={isPositive ? 'positive' : ''}>
        {value}
      </strong>
    </article>
  );
}

function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="logo">
        <span className="logo-mark">◆</span>
        <span>LotStack</span>
      </div>

      <nav className="sidebar-navigation">
        <button className="navigation-button active">
          Dashboard
        </button>

        <button className="navigation-button">
          Vehicles
        </button>

        <button className="navigation-button">
          About
        </button>
      </nav>
    </aside>
  );
}

interface VehicleTableProps {
  vehicles: Vehicle[];
  onSelect: (vehicle: Vehicle) => void;
}

function VehicleTable({
  vehicles,
  onSelect,
}: VehicleTableProps) {
  return (
    <section className="table-card">
      <div className="table-toolbar">
        <input
          type="search"
          placeholder="Search vehicles..."
          aria-label="Search vehicles"
        />

        <select defaultValue="all">
          <option value="all">All Vehicles</option>
          <option value="sold">Sold</option>
          <option value="under-repair">Under Repair</option>
          <option value="available">Available</option>
          <option value="presold">Presold</option>
        </select>

        <select defaultValue="newest">
          <option value="newest">Newest First</option>
          <option value="highest-roi">Highest ROI</option>
          <option value="highest-profit">Highest Profit</option>
        </select>
      </div>

      <div className="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Vehicle</th>
              <th>Purchase Price</th>
              <th>Cost to Fix</th>
              <th>Total Invested</th>
              <th>Sold For</th>
              <th>Profit</th>
              <th>ROI</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>
            {vehicles.map((vehicle) => {
              const repairCost = calculateRepairCost(vehicle);
              const totalInvested =
                calculateTotalInvested(vehicle);
              const profit = calculateProfit(vehicle);
              const roi = calculateRoi(vehicle);
              const hasSalePrice = vehicle.soldPrice !== null;

              return (
                <tr key={vehicle.id}>
                  <td>
                    <div className="vehicle-cell">
                      <img
                        src={vehicle.thumbnailUrl}
                        alt={`${vehicle.year} ${vehicle.make} ${vehicle.model}`}
                        onError={handleImageError}
                      />

                      <div>
                        <strong>
                          {vehicle.year} {vehicle.make}
                        </strong>

                        <span>{vehicle.model}</span>
                      </div>
                    </div>
                  </td>

                  <td>{formatCurrency(vehicle.purchasePrice)}</td>
                  <td>{formatCurrency(repairCost)}</td>
                  <td>{formatCurrency(totalInvested)}</td>
                  <td>{formatSalePrice(vehicle)}</td>

                  <td className={hasSalePrice ? (profit >= 0 ? 'positive' : 'negative') : ''}>
                    {hasSalePrice ? formatCurrency(profit) : 'Pending'}
                  </td>

                  <td className={roi === null ? '' : (roi >= 0 ? 'positive' : 'negative')}>
                    {roi === null ? 'Pending' : `${roi.toFixed(1)}%`}
                  </td>

                  <td>
                    <span
                      className="status-badge"
                      style={getStatusStyle(vehicle.status)}
                    >
                      {vehicle.status}
                    </span>
                  </td>

                  <td>
                    <button
                      className="details-button"
                      onClick={() => onSelect(vehicle)}
                    >
                      View Details
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </section>
  );
}

interface VehicleDetailsProps {
  vehicle: Vehicle;
  onClose: () => void;
}

function VehicleDetails({
  vehicle,
  onClose,
}: VehicleDetailsProps) {
  const totalInvested = calculateTotalInvested(vehicle);
  const profit = calculateProfit(vehicle);
  const roi = calculateRoi(vehicle);

  return (
    <section className="details-card">
      <button className="back-button" onClick={onClose}>
        ← Back to vehicles
      </button>

      <div className="details-heading">
        <span
          className="status-badge"
          style={getStatusStyle(vehicle.status)}
        >
          {vehicle.status}
        </span>

        <h2>
          {vehicle.year} {vehicle.make} {vehicle.model}
        </h2>

        <p>{vehicle.trim} · {vehicle.mileage === null ? 'Mileage pending' : `${vehicle.mileage.toLocaleString()} miles`}</p>
      </div>

      <div className="details-images">
        {vehicle.imageUrls.map((imageUrl) => (
          <img
            key={imageUrl}
            src={imageUrl}
            alt={`${vehicle.year} ${vehicle.make} ${vehicle.model}`}
            onError={handleImageError}
          />
        ))}
      </div>

      <div className="details-grid">
        <div>
          <span>VIN</span>
          <strong>{vehicle.vin}</strong>
        </div>

        <div>
          <span>Mileage</span>
          <strong>
            {vehicle.mileage === null
              ? 'Pending verification'
              : `${vehicle.mileage.toLocaleString()} miles`}
          </strong>
        </div>

        <div>
          <span>Lot Number</span>
          <strong>{vehicle.lotNumber}</strong>
        </div>

        <div>
          <span>Total Invested</span>
          <strong>{formatCurrency(totalInvested)}</strong>
        </div>

        <div>
          <span>Sold For</span>
          <strong>{formatSalePrice(vehicle)}</strong>
        </div>

        <div>
          <span>Final Profit</span>
          <strong className={vehicle.soldPrice === null ? '' : (profit >= 0 ? 'positive' : 'negative')}>
            {vehicle.soldPrice === null ? 'Pending' : formatCurrency(profit)}
          </strong>
        </div>

        <div>
          <span>ROI</span>
          <strong className={roi === null ? '' : (roi >= 0 ? 'positive' : 'negative')}>
            {roi === null ? 'Pending' : `${roi.toFixed(1)}%`}
          </strong>
        </div>

        <div>
          <span>Auction Listing</span>

          {vehicle.originalListingUrl ? (
            <a className="details-link" href={vehicle.originalListingUrl} target="_blank" rel="noreferrer">
              View Original Listing ↗
            </a>
          ) : (
            <strong>N/A</strong>
          )}
        </div>
      </div>

      <div className="expense-section">
        <h3>Expenses</h3>

        {vehicle.expenses.map((expense) => (
          <div className="expense-row" key={expense.id}>
            <div>
              <strong>{expense.description}</strong>

              <span>
                {expense.category.replaceAll('_', ' ')}
              </span>
            </div>

            <strong>{formatCurrency(expense.amount)}</strong>
          </div>
        ))}
      </div>

      {vehicle.notes && (
        <div className="notes-section">
          <h3>Notes</h3>
          <p>{vehicle.notes}</p>
        </div>
      )}
    </section>
  );
}

function App() {
  const [selectedVehicle, setSelectedVehicle] =
    useState<Vehicle | null>(null);

  const totalCapitalInvested = vehicles.reduce(
    (total, vehicle) =>
      total + calculateTotalInvested(vehicle),
    0
  );

  const soldVehicles = vehicles.filter(
    (vehicle) => vehicle.status === 'Sold' && vehicle.soldPrice !== null
  );

  const totalRevenue = soldVehicles.reduce(
    (total, vehicle) => total + (vehicle.soldPrice ?? 0),
    0
  );

  const totalProfit = soldVehicles.reduce(
    (total, vehicle) => total + calculateProfit(vehicle),
    0
  );

  const averageRoi =
    soldVehicles.length === 0
      ? 0
      : soldVehicles.reduce(
          (total, vehicle) =>
            total + (calculateRoi(vehicle) ?? 0),
          0
        ) / soldVehicles.length;

  const metrics: MetricCardProps[] = [
    {
      title: 'Total Capital Invested',
      value: formatCurrency(totalCapitalInvested),
    },
    {
      title: 'Total Gross Profit',
      value: formatCurrency(totalProfit),
      isPositive: totalProfit >= 0,
    },
    {
      title: 'Total Revenue',
      value: formatCurrency(totalRevenue),
    },
    {
      title: 'Average ROI',
      value: `${averageRoi.toFixed(1)}%`,
      isPositive: averageRoi >= 0,
    },
  ];

  return (
    <div className="app-layout">
      <Sidebar />

      <main className="main-content">
        <header className="page-header">
          <h1>
            {selectedVehicle
              ? 'Vehicle Details'
              : 'Vehicle Portfolio'}
          </h1>
        </header>

        {!selectedVehicle && (
          <section className="metrics-grid">
            {metrics.map((metric) => (
              <MetricCard
                key={metric.title}
                {...metric}
              />
            ))}
          </section>
        )}

        {selectedVehicle ? (
          <VehicleDetails
            vehicle={selectedVehicle}
            onClose={() => setSelectedVehicle(null)}
          />
        ) : (
          <VehicleTable
            vehicles={vehicles}
            onSelect={setSelectedVehicle}
          />
        )}
      </main>
    </div>
  );
}

export default App;