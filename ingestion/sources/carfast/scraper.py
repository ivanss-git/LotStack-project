"""
CarFast integration.

Status:
    Blocked because CarFast's structured site endpoints return a
    Cloudflare verification response to direct Python requests.

Next steps:
    - Request authorized API access from CarFast.
    - Investigate an officially supported data feed.
    - Use manually supplied CarFast listing URLs when necessary.
"""


def run_scan():
    """Explain why automated collection is currently unavailable."""

    raise RuntimeError(
        "CarFast automated collection is unavailable because "
        "direct requests are blocked by Cloudflare."
    )


if __name__ == "__main__":
    print(
        "CarFast integration is currently blocked. "
        "Use the working LSO collector instead."
    )
