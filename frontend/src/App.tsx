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
  mileage: number;

  thumbnailUrl: string;
  imageUrls: string[];

  purchasePrice: number;
  soldPrice: number;
  expenses: Expense[];

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
    make: 'Honda',
    model: 'Accord Sport',
    vin: '1HGCV1F34JA123456',
    mileage: 84200,

    thumbnailUrl: '/images/accord-front.jpg',
    imageUrls: [
      '/images/accord-front.jpg',
      '/images/accord-rear.jpg',
      '/images/accord-interior.jpg',
    ],

    purchasePrice: 6000,
    soldPrice: 8000,

    expenses: [
      {
        id: 1,
        description: 'Front bumper replacement',
        category: 'REPAIR',
        amount: 450,
      },
      {
        id: 2,
        description: 'Paint and body work',
        category: 'REPAIR',
        amount: 300,
      },
      {
        id: 3,
        description: 'Oil change and inspection',
        category: 'REPAIR',
        amount: 150,
      },
      {
        id: 4,
        description: 'Transportation',
        category: 'TRANSPORT',
        amount: 200,
      },
    ],

    lotNumber: 'LOT-12345',
    originalListingUrl: 'https://example.com/listing/12345',
    notes: 'Minor front-end damage. Vehicle ran and drove.',
  },
  {
    id: 2,
    year: 2019,
    make: 'Toyota',
    model: 'Camry SE',
    vin: '4T1B11HK5KU123456',
    mileage: 76500,

    thumbnailUrl: '/images/camry-front.jpg',
    imageUrls: [
      '/images/camry-front.jpg',
      '/images/camry-rear.jpg',
      '/images/camry-interior.jpg',
    ],

    purchasePrice: 7000,
    soldPrice: 9800,

    expenses: [
      {
        id: 5,
        description: 'Rear bumper repair',
        category: 'REPAIR',
        amount: 650,
      },
      {
        id: 6,
        description: 'Paint work',
        category: 'REPAIR',
        amount: 400,
      },
      {
        id: 7,
        description: 'Detailing',
        category: 'REPAIR',
        amount: 150,
      },
      {
        id: 8,
        description: 'Auction fee',
        category: 'AUCTION_FEE',
        amount: 250,
      },
    ],

    lotNumber: 'LOT-67890',
    originalListingUrl: 'https://example.com/listing/67890',
    notes: 'Clean title with cosmetic rear damage.',
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
  return vehicle.soldPrice - calculateTotalInvested(vehicle);
}

function calculateRoi(vehicle: Vehicle): number {
  const totalInvested = calculateTotalInvested(vehicle);

  if (totalInvested === 0) {
    return 0;
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

function handleImageError(
  event: React.SyntheticEvent<HTMLImageElement>
) {
  event.currentTarget.onerror = null;
  event.currentTarget.src = '/images/car-placeholder.jpg';
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
                  <td>{formatCurrency(vehicle.soldPrice)}</td>

                  <td className={profit >= 0 ? 'positive' : 'negative'}>
                    {formatCurrency(profit)}
                  </td>

                  <td className={roi >= 0 ? 'positive' : 'negative'}>
                    {roi.toFixed(1)}%
                  </td>

                  <td>
                    <span className="status-badge">Sold</span>
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
        <span className="status-badge">Sold</span>

        <h2>
          {vehicle.year} {vehicle.make} {vehicle.model}
        </h2>

        <p>{vehicle.mileage.toLocaleString()} miles</p>
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
            {vehicle.mileage.toLocaleString()} miles
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
          <strong>{formatCurrency(vehicle.soldPrice)}</strong>
        </div>

        <div>
          <span>Final Profit</span>
          <strong className={profit >= 0 ? 'positive' : 'negative'}>
            {formatCurrency(profit)}
          </strong>
        </div>

        <div>
          <span>ROI</span>
          <strong className={roi >= 0 ? 'positive' : 'negative'}>
            {roi.toFixed(1)}%
          </strong>
        </div>

        <div>
          <span>Auction Listing</span>

          <a
            className="details-link"
            href={vehicle.originalListingUrl}
            target="_blank"
            rel="noreferrer"
          >
            View Original Listing ↗
          </a>
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

  const totalRevenue = vehicles.reduce(
    (total, vehicle) => total + vehicle.soldPrice,
    0
  );

  const totalProfit = vehicles.reduce(
    (total, vehicle) => total + calculateProfit(vehicle),
    0
  );

  const averageRoi =
    vehicles.length === 0
      ? 0
      : vehicles.reduce(
          (total, vehicle) =>
            total + calculateRoi(vehicle),
          0
        ) / vehicles.length;

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