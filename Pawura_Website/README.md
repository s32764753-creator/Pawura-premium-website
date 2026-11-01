# Pawura premium pet shop website

Responsive static storefront prototype with:
- Premium responsive homepage and navigation
- Product catalog with category filters and search
- Shopping bag with quantity controls and browser-local persistence
- Demo checkout form
- Newsletter demo form

## Preview locally
Open `index.html` in a modern browser. For best results, run a local static server (for example, `python -m http.server 8000`) from this folder.

## Publish for free with GitHub Pages
1. Create a GitHub repository, e.g. `pawura-website`.
2. Upload the **contents** of this folder to the repository root (index.html must be at the root).
3. In GitHub open Settings → Pages.
4. Under Build and deployment choose Deploy from a branch, select `main` and `/ (root)`, then Save.
5. GitHub will show the published site URL after deployment.

Alternative: deploy this folder to Netlify by dragging it into the Netlify deploy page.

## Custom domain pawura.net
The domain has not been purchased or registered by this project. First check whether `pawura.net` is available with a domain registrar and register it in your own account. Then add `pawura.net` under your hosting provider's custom-domain settings and apply the DNS records they give you. DNS propagation can take time.

## Important functionality limits
This is a front-end prototype, not yet a live commercial store. Product names, prices and stock are sample content. Cart contents are saved only in the visitor's browser. Checkout does not transmit orders, take payments, calculate shipping, or contact a shop. Newsletter signups are not stored. Before accepting real orders, connect a secure backend/database, inventory, order notifications, shipping rules, privacy/terms pages and a payment provider suitable for your country.
