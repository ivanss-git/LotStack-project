import {
  useState,
  type ReactNode,
  type SyntheticEvent,
} from 'react';
import './App.css';

/* ---------- Types ---------- */

type Page = 'dashboard' | 'about';
type VehicleStatus = 'Sold' | 'Under Repair' | 'Available' | 'Presold';
type StatusFilter = 'all' | VehicleStatus;
type SortOption = 'newest' | 'highest-roi' | 'highest-profit';

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

  status: VehicleStatus;

  lotNumber: string;
  originalListingUrl: string;
  notes?: string;
}

interface MetricCardProps {
  title: string;
  value: string;
  isPositive?: boolean;
}

/* ---------- Fixed display values ---------- */

const PAGES: Page[] = ['dashboard', 'about'];

const STATUS_STYLES: Record<
  VehicleStatus,
  { backgroundColor: string; color: string }
> = {
  Sold: { backgroundColor: '#dcfce7', color: '#166534' },
  Presold: { backgroundColor: '#fef3c7', color: '#92400e' },
  'Under Repair': { backgroundColor: '#ffedd5', color: '#9a3412' },
  Available: { backgroundColor: '#fee2e2', color: '#b91c1c' },
};

/* ---------- Vehicle portfolio data ---------- */

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
    lotNumber: '58134945', originalListingUrl: 'https://bidmotors.bg/en/gmc-sierra-k3500-2018-1gd42vcy3jf249235',
    notes: 'Replacement panels matched the vehicle color, so paint was not needed.',
  },
  {
    id: 2, year: 2018, make: 'GMC', model: 'Sierra K1500', trim: 'Denali',
    vin: '3GTU2PEC8JG526641', mileage: null,
    thumbnailUrl: '/images/pickups/2018-denali-black.jpeg', imageUrls: ['/images/pickups/2018-denali-black.jpeg'],
    purchasePrice: 14800, soldPrice: null, status: 'Under Repair',
    expenses: [
      { id: 201, description: 'Front-end suspension kit', category: 'REPAIR', amount: 750 },
      { id: 203, description: 'Alignment', category: 'REPAIR', amount: 100 },
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
      { id: 303, description: 'Alignment', category: 'REPAIR', amount: 100 },
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
    notes: 'VIN pending verification. The listed fees total $2,796. Repair total: $2,385.90.',
  },
  {
    id: 4, year: 1997, make: 'Chevrolet', model: 'C1500', trim: 'Base',
    vin: 'Pending verification', mileage: null,
    thumbnailUrl: '/images/pickups/1997-chevrolet-c1500.jpeg', imageUrls: ['/images/pickups/1997-chevrolet-c1500.jpeg'],
    purchasePrice: 1500, soldPrice: 6700, status: 'Sold',
    expenses: [
      { id: 401, description: 'Engine Rebuild', category: 'REPAIR', amount: 1800 },
      { id: 402, description: 'Transmission Rebuild', category: 'REPAIR', amount: 750 },
      { id: 403, description: "Paint Job - Labor was free", category: 'REPAIR', amount: 250}
    ],
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
      { id: 504, description: 'Buyer fee', category: 'AUCTION_FEE', amount: 300 },
    ],
    lotNumber: '902-EQP-94019', originalListingUrl: 'https://www.lso.cc/auction/7729/item/2009-honda-civic-gx-cng-229091/',
  },
  {
    id: 6, year: 2017, make: 'Volkswagen', model: 'Jetta', trim: 'S',
    vin: '3VW2B7AJ3HM250759', mileage: null,
    thumbnailUrl: '/images/car-icon.jpg', imageUrls: ['/images/car-icon.jpg'],
    purchasePrice: 4800, soldPrice: 7500, status: 'Sold', expenses: [],
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
    purchasePrice: 1500, soldPrice: 5300, status: 'Sold',
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
    thumbnailUrl: '/images/pickups/2015-chevrolet-silverado-red.jpeg', imageUrls: ['/images/pickups/2015-silverado-red.jpeg'],
    purchasePrice: 7500, soldPrice: 16500, status: 'Sold',
    expenses: [
      { id: 1001, description: 'Door', category: 'REPAIR', amount: 535 },
      { id: 1002, description: 'Bed', category: 'REPAIR', amount: 700 },
      { id: 1003, description: 'Bed and door paint', category: 'REPAIR', amount: 540 },
      { id: 1004, description: 'Starter', category: 'REPAIR', amount: 120 },
      { id: 1005, description: 'Cabin work', category: 'REPAIR', amount: 800 },
      { id: 1007, description: 'Tow', category: 'TRANSPORT', amount: 300 },
    ],
    lotNumber: 'N/A', originalListingUrl: '',
    notes: 'Sold for $16,500 to the same client who purchased the Sierra 3500HD.',
  },
  {
    id: 11, year: 2017, make: 'Chevrolet', model: 'Silverado', trim: 'LS',
    vin: '3GCPCNECXHG331651', mileage: 75345,
    thumbnailUrl: '/images/pickups/2017-chevrolet-silverado-white.jpeg', imageUrls: ['/images/pickups/2017-chevrolet-silverado-white.jpeg'],
    purchasePrice: 3000, soldPrice: null, status: 'Under Repair',
    expenses: [
      { id: 1101, description: 'Door - Driver', category: 'REPAIR', amount: 400 },
      { id: 1102, description: 'Door - Driver (rear)', category: 'REPAIR', amount: 400 },
      { id: 1103, description: 'Front Bumper Assembly', category: 'REPAIR', amount: 320 },
      { id: 1104, description: 'Front Fenders', category: 'REPAIR', amount: 400 },
      { id: 1105, description: 'Radiator and Core Support', category: 'REPAIR', amount: 250 },
      { id: 1106, description: 'Hood', category: 'REPAIR', amount: 300 },
      { id: 1107, description: 'Side Rear View Mirrors Pair', category: 'REPAIR', amount: 300 },
      { id: 1108, description: 'Rebuilt Engine', category: 'REPAIR', amount: 2900 },
      { id: 1109, description: 'Cargo Light Assembly', category: 'REPAIR', amount: 30},
      { id: 1110, description: 'Airbag and Module Reset', category: 'REPAIR', amount: 450 },
    ],
    lotNumber: '57716085', originalListingUrl: 'https://bidmotors.bg/en/chevrolet-all-models-c1500-2017-3gcpcnecxhg331651',
    notes: 'Currently undergoing further repairs.'
  
  },
];

/* ---------- Calculations and formatting ---------- */

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
  return vehicle.soldPrice === null
    ? 0
    : vehicle.soldPrice - calculateTotalInvested(vehicle);
}

function calculateRoi(vehicle: Vehicle): number | null {
  if (vehicle.soldPrice === null) return null;
  const totalInvested = calculateTotalInvested(vehicle);

  if (totalInvested === 0) return null;

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

function formatMileage(mileage: number | null): string {
  return mileage === null
    ? 'Pending verification'
    : `${mileage.toLocaleString()} miles`;
}

function getValueClass(value: number | null): string {
  if (value === null) return '';
  return value >= 0 ? 'positive' : 'negative';
}

function handleImageError(event: SyntheticEvent<HTMLImageElement>) {
  event.currentTarget.onerror = null;
  event.currentTarget.src = '/images/car-icon.jpg';
}

function getStatusStyle(status: VehicleStatus) {
  return STATUS_STYLES[status];
}

/* ---------- Reusable UI components ---------- */

function MetricCard({
  title,
  value,
  isPositive = false,
}: Readonly<MetricCardProps>) {
  return (
    <article className="metric-card">
      <span>{title}</span>

      <strong className={isPositive ? 'positive' : ''}>
        {value}
      </strong>
    </article>
  );
}

interface SidebarProps {
  activePage: Page;
  onNavigate: (page: Page) => void;
}

function Sidebar({
  activePage,
  onNavigate,
}: Readonly<SidebarProps>) {
  return (
    <aside className="sidebar">
      <div className="logo">
        <span className="logo-mark">◆</span>
        <span>LotStack</span>
      </div>

      <nav className="sidebar-navigation">
        {PAGES.map((page) => (
          <button
            key={page}
            type="button"
            className={`navigation-button ${activePage === page ? 'active' : ''}`}
            onClick={() => onNavigate(page)}
          >
            {page[0].toUpperCase() + page.slice(1)}
          </button>
        ))}
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
}: Readonly<VehicleTableProps>) {
  const [searchQuery, setSearchQuery] = useState('');
  const [statusFilter, setStatusFilter] = useState<StatusFilter>('all');
  const [sortOption, setSortOption] = useState<SortOption>('newest');

  const displayedVehicles = vehicles
    .filter((vehicle) => {
      const query = searchQuery.trim().toLowerCase();
      const matchesSearch =
        query === '' ||
        `${vehicle.year} ${vehicle.make} ${vehicle.model} ${vehicle.trim} ${vehicle.vin}`
          .toLowerCase()
          .includes(query);
      const matchesStatus =
        statusFilter === 'all' || vehicle.status === statusFilter;

      return matchesSearch && matchesStatus;
    })
    .sort((a, b) => {
      if (sortOption === 'highest-profit') {
        return calculateProfit(b) - calculateProfit(a);
      }

      if (sortOption === 'highest-roi') {
        return (calculateRoi(b) ?? -Infinity) - (calculateRoi(a) ?? -Infinity);
      }

      return b.year - a.year || b.id - a.id;
    });

  return (
    <section className="table-card">
      <div className="table-toolbar">
        <input
          type="search"
          placeholder="Search vehicles..."
          aria-label="Search vehicles"
          value={searchQuery}
          onChange={(event) => setSearchQuery(event.target.value)}
        />

        <select
          value={statusFilter}
          onChange={(event) => setStatusFilter(event.target.value as StatusFilter)}
        >
          <option value="all">All Vehicles</option>
          <option value="Sold">Sold</option>
          <option value="Under Repair">Under Repair</option>
          <option value="Available">Available</option>
          <option value="Presold">Presold</option>
        </select>

        <select
          value={sortOption}
          onChange={(event) => setSortOption(event.target.value as SortOption)}
        >
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
            {displayedVehicles.map((vehicle) => {
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

                  <td className={getValueClass(hasSalePrice ? profit : null)}>
                    {hasSalePrice ? formatCurrency(profit) : 'Pending'}
                  </td>

                  <td className={getValueClass(roi)}>
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
                      type="button"
                      className="details-button"
                      onClick={() => onSelect(vehicle)}
                    >
                      View Details
                    </button>
                  </td>
                </tr>
              );
            })}

            {displayedVehicles.length === 0 && (
              <tr>
                <td colSpan={9}>No vehicles match your search.</td>
              </tr>
            )}
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
}: Readonly<VehicleDetailsProps>) {
  const totalInvested = calculateTotalInvested(vehicle);
  const profit = calculateProfit(vehicle);
  const roi = calculateRoi(vehicle);

  return (
    <section className="details-card">
      <button type="button" className="back-button" onClick={onClose}>
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

        <p>
          {vehicle.trim} · {vehicle.mileage === null
            ? 'Mileage pending'
            : formatMileage(vehicle.mileage)}
        </p>
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
          <strong>{formatMileage(vehicle.mileage)}</strong>
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
          <strong className={getValueClass(vehicle.soldPrice === null ? null : profit)}>
            {vehicle.soldPrice === null ? 'Pending' : formatCurrency(profit)}
          </strong>
        </div>

        <div>
          <span>ROI</span>
          <strong className={getValueClass(roi)}>
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

function About() {
  return (
    <section className="details-card">
      <h2>About LotStack</h2>

      <p>
        LotStack displays the purchases, expenses, and outcomes of vehicles
        found through the LotStack auction analyzer.
      </p>

      <p>
        It is a personal full-stack project inspired by firsthand experience
        buying, repairing, and selling auction vehicles. The project combines
        real transaction results with software designed to evaluate auction
        opportunities.
      </p>

      <div className="notes-section">
        <h3>How results are calculated</h3>
        <p>
          Net profit equals the final sale price minus the purchase price and
          all recorded expenses. ROI equals net profit divided by total invested.
        </p>
      </div>
    </section>
  );
}

/* ---------- Main application ---------- */

function App() {
  const [activePage, setActivePage] = useState<Page>('dashboard');
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
      title: 'Total Net Profit',
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

  // Choose the heading and content without nested ternaries.
  let pageTitle = 'Vehicle Portfolio';
  let pageContent: ReactNode;

  if (activePage === 'about') {
    pageTitle = 'About LotStack';
    pageContent = <About />;
  } else if (selectedVehicle) {
    pageTitle = 'Vehicle Details';
    pageContent = (
      <VehicleDetails
        vehicle={selectedVehicle}
        onClose={() => setSelectedVehicle(null)}
      />
    );
  } else {
    pageContent = (
      <VehicleTable
        vehicles={vehicles}
        onSelect={setSelectedVehicle}
      />
    );
  }

  return (
    <div className="app-layout">
      <Sidebar
        activePage={activePage}
        onNavigate={(page) => {
          setActivePage(page);
          setSelectedVehicle(null);
        }}
      />

      <main className="main-content">
        <header className="page-header">
          <h1>{pageTitle}</h1>
        </header>

        {activePage === 'dashboard' && !selectedVehicle && (
          <section className="metrics-grid">
            {metrics.map((metric) => (
              <MetricCard
                key={metric.title}
                {...metric}
              />
            ))}
          </section>
        )}

        {pageContent}
      </main>
    </div>
  );
}

export default App;
