#!/usr/bin/env bash

set -euo pipefail

BASE_URL="${CAR_ANALYZER_URL:-http://localhost:8080}"
METRIC="${1:-OPPORTUNITY}"
LIMIT="${2:-10}"

echo "Refreshing analyses..."

curl --fail --silent --show-error \
  -X POST \
  "$BASE_URL/api/demo/listing-analysis/refresh" \
  | jq '.'

echo
echo "Global top $LIMIT by $METRIC"

curl --fail --silent --show-error \
  "$BASE_URL/api/demo/rankings?limit=$LIMIT&sortBy=$METRIC" \
  | jq '{
      generatedAt,
      rankedBy,
      sitesCompared,
      vehiclesCompared,
      globalTop10,
      siteTop10
    }'