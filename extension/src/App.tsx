import {
  useCallback,
  useEffect,
  useMemo,
  useState,
} from "react";

import "./App.css";

import { getRankings } from "./rankingApi";

import type {
  RankedListing,
  RankingMetric,
  RankingResponse,
} from "./rankingTypes";

const moneyFormatter = new Intl.NumberFormat(
  "en-US",
  {
    style: "currency",
    currency: "USD",
    maximumFractionDigits: 0,
  },
);

type ViewName = "GLOBAL" | string;

function formatMoney(value: number): string {
  return moneyFormatter.format(value);
}

function formatMileage(
  mileage: number | null,
): string {
  if (mileage === null) {
    return "Mileage unknown";
  }

  return `${mileage.toLocaleString()} miles`;
}

function App() {
  const [ranking, setRanking] =
    useState<RankingResponse | null>(null);

  const [metric, setMetric] =
    useState<RankingMetric>("OPPORTUNITY");

  const [selectedView, setSelectedView] =
    useState<ViewName>("GLOBAL");

  const [loading, setLoading] = useState(true);
  const [error, setError] =
    useState<string | null>(null);

  const loadRankings = useCallback(async () => {
    setLoading(true);
    setError(null);

    try {
      const result = await getRankings(
        metric,
        10,
      );

      setRanking(result);
    } catch (caughtError: unknown) {
      const message =
        caughtError instanceof Error
          ? caughtError.message
          : "Unable to load rankings.";

      setError(
        `${message} Confirm that the Spring Boot backend is running.`,
      );
    } finally {
      setLoading(false);
    }
  }, [metric]);

  useEffect(() => {
    void loadRankings();
  }, [loadRankings]);

  const providers = useMemo(() => {
    if (ranking === null) {
      return [];
    }

    return Object.keys(ranking.siteTop10);
  }, [ranking]);

  const displayedVehicles =
    useMemo<RankedListing[]>(() => {
      if (ranking === null) {
        return [];
      }

      if (selectedView === "GLOBAL") {
        return ranking.globalTop10;
      }

      return ranking.siteTop10[selectedView] ?? [];
    }, [ranking, selectedView]);

  useEffect(() => {
    if (
      selectedView !== "GLOBAL"
      && !providers.includes(selectedView)
    ) {
      setSelectedView("GLOBAL");
    }
  }, [providers, selectedView]);

  return (
    <main className="extension">
      <header className="extension-header">
        <div>
          <h1>LotStack</h1>
          <p>Vehicle opportunity rankings</p>
        </div>

        <button
          className="refresh-button"
          type="button"
          onClick={() => void loadRankings()}
          disabled={loading}
        >
          {loading ? "Loading…" : "Refresh"}
        </button>
      </header>

      <section className="ranking-controls">
        <label htmlFor="ranking-metric">
          Rank by
        </label>

        <select
          id="ranking-metric"
          value={metric}
          onChange={(event) => {
            setMetric(
              event.target.value as RankingMetric,
            );
          }}
        >
          <option value="OPPORTUNITY">
            Opportunity
          </option>

          <option value="ROI">
            Estimated ROI
          </option>

          <option value="PROFIT">
            Expected profit
          </option>
        </select>
      </section>

      {ranking !== null && (
        <section className="summary">
          <div>
            <strong>
              {ranking.vehiclesCompared}
            </strong>
            <span>Vehicles</span>
          </div>

          <div>
            <strong>
              {ranking.sitesCompared}
            </strong>
            <span>Sites</span>
          </div>

          <div>
            <strong>
              {ranking.globalTop10.length}
            </strong>
            <span>Top results</span>
          </div>
        </section>
      )}

      {ranking !== null && (
        <nav
          className="site-tabs"
          aria-label="Ranking source"
        >
          <button
            type="button"
            className={
              selectedView === "GLOBAL"
                ? "active"
                : ""
            }
            onClick={() => {
              setSelectedView("GLOBAL");
            }}
          >
            Global
          </button>

          {providers.map((provider) => (
            <button
              key={provider}
              type="button"
              className={
                selectedView === provider
                  ? "active"
                  : ""
              }
              onClick={() => {
                setSelectedView(provider);
              }}
            >
              {provider}
            </button>
          ))}
        </nav>
      )}

      {error !== null && (
        <p className="error-message">
          {error}
        </p>
      )}

      {!loading
        && error === null
        && displayedVehicles.length === 0 && (
          <p className="empty-message">
            No analyzed vehicles are available.
          </p>
        )}

      <ol className="ranking-list">
        {displayedVehicles.map(
          (vehicle, index) => (
            <li
              key={vehicle.sourceRecordId}
              className="vehicle-card"
            >
              <div className="vehicle-heading">
                <span className="rank">
                  {index + 1}
                </span>

                <div className="vehicle-name">
                  <strong>
                    {vehicle.year}{" "}
                    {vehicle.make}{" "}
                    {vehicle.model}
                  </strong>

                  <span>
                    {vehicle.provider}
                    {vehicle.location !== null
                      ? ` · ${vehicle.location}`
                      : ""}
                  </span>
                </div>

                <span
                  className={`risk risk-${vehicle.riskLevel.toLowerCase()}`}
                >
                  {vehicle.riskLevel}
                </span>
              </div>

              <div className="vehicle-financials">
                <div>
                  <span>Score</span>
                  <strong>
                    {vehicle.opportunityScore}
                    /100
                  </strong>
                </div>

                <div>
                  <span>Est. purchase</span>
                  <strong>
                    {formatMoney(
                      vehicle.estimatedPurchasePrice,
                    )}
                  </strong>
                </div>

                <div>
                  <span>Market value</span>
                  <strong>
                    {formatMoney(
                      vehicle.estimatedMarketValue,
                    )}
                  </strong>
                </div>

                <div>
                  <span>Repairs</span>
                  <strong>
                    {formatMoney(
                      vehicle.estimatedRepairCost,
                    )}
                  </strong>
                </div>

                <div>
                  <span>Profit</span>
                  <strong>
                    {formatMoney(
                      vehicle.expectedProfit,
                    )}
                  </strong>
                </div>

                <div>
                  <span>ROI</span>
                  <strong>
                    {vehicle.roiPercent.toFixed(1)}%
                  </strong>
                </div>
              </div>

              <div className="vehicle-footer">
                <span>
                  {formatMileage(vehicle.mileage)}
                </span>

                <span
                  className={
                    vehicle.goodCandidate
                      ? "candidate-good"
                      : "candidate-review"
                  }
                >
                  {vehicle.goodCandidate
                    ? "Good candidate"
                    : "Review needed"}
                </span>
              </div>
            </li>
          ),
        )}
      </ol>

      {ranking !== null && (
        <footer className="last-updated">
          Updated{" "}
          {new Date(
            ranking.generatedAt,
          ).toLocaleString()}
        </footer>
      )}
    </main>
  );
}

export default App;