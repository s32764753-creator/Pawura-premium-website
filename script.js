const products = [
  {id:1,name:"Daily Goodness Dog Food",type:"Dogs",category:"DOG NUTRITION",price:189000,emoji:"🥣",tag:"BESTSELLER",art:"dog"},
  {id:2,name:"Wholesome Cat Kibble",type:"Cats",category:"CAT NUTRITION",price:165000,emoji:"🐱",tag:"EVERYDAY FAVOURITE",art:"cat"},
  {id:3,name:"The Happy Pup Rope Toy",type:"Toys",category:"PLAY & ENRICHMENT",price:79000,emoji:"🪢",tag:"PLAYTIME PICK",art:"toy"},
  {id:4,name:"Cloud Nine Pet Cushion",type:"Care",category:"CARE & COMFORT",price:245000,emoji:"🛏️",tag:"SOFT & COSY",art:"care"},
  {id:5,name:"Tasty Bites Training Treats",type:"Dogs",category:"DOG TREATS",price:59000,emoji:"🦴",tag:"LITTLE REWARD",art:"dog"},
  {id:6,name:"Playful Cat Wand",type:"Toys",category:"PLAY & ENRICHMENT",price:49000,emoji:"🐾",tag:"CAT APPROVED",art:"toy"},
  {id:7,name:"Gentle Grooming Brush",type:"Care",category:"CARE & COMFORT",price:89000,emoji:"🪮",tag:"DAILY CARE",art:"care"},
  {id:8,name:"Kitty Comfort Bowl",type:"Cats",category:"CAT ESSENTIALS",price:69000,emoji:"🥛",tag:"THOUGHTFUL PICK",art:"cat"}
];
const money = n => new Intl.NumberFormat("en-US").format(n) + " UZS";
let cart = JSON.parse(localStorage.getItem("pawura-cart") || "[]");
let activeCategory = "All", searchTerm = "";
const productsEl=document.getElementById("products"), cartDrawer=document.getElementById("cartDrawer"), overlay=document.getElementById("overlay"), toast=document.getElementById("toast");
function renderProducts(){
 const filtered=products.filter(p=>(activeCategory==="All"||p.type===activeCategory)&&(`${p.name} ${p.category} ${p.type}`).toLowerCase().includes(searchTerm));
 productsEl.innerHTML=filtered.length?filtered.map(p=>`<article class="product-card"><div class="product-image ${p.art}"><span class="product-tag">${p.tag}</span><span class="product-art" aria-hidden="true">${p.emoji}</span><button class="add-button" data-add="${p.id}" aria-label="Add ${p.name} to bag">+</button></div><div class="product-info"><div class="product-type">${p.category}</div><h3 class="product-name">${p.name}</h3><div class="product-bottom"><span class="product-price">${money(p.price)}</span><span class="product-rating">★★★★★</span></div></div></article>`).join(""):`<p>No products found. Try another search.</p>`;
}
function saveCart(){localStorage.setItem("pawura-cart",JSON.stringify(cart));renderCart()}
function addToCart(id){const item=cart.find(x=>x.id===id);item?item.qty++:cart.push({id,qty:1});saveCart();showToast("Added to your Pawura bag ♡")}
function renderCart(){
 const count=cart.reduce((n,x)=>n+x.qty,0),total=cart.reduce((n,x)=>n+products.find(p=>p.id===x.id).price*x.qty,0);
 document.getElementById("cartCount").textContent=count;document.getElementById("drawerCount").textContent=`(${count})`;document.getElementById("subtotal").textContent=money(total);document.getElementById("checkoutTotal").textContent=money(total);
 document.getElementById("cartItems").innerHTML=cart.length?cart.map(x=>{const p=products.find(p=>p.id===x.id);return `<div class="cart-line"><div class="cart-thumb">${p.emoji}</div><div><h4>${p.name}</h4><small>${money(p.price)}</small><div class="quantity"><button data-qty="${p.id}" data-change="-1" aria-label="Decrease quantity">−</button><span>${x.qty}</span><button data-qty="${p.id}" data-change="1" aria-label="Increase quantity">+</button></div></div><strong class="line-price">${money(p.price*x.qty)}</strong></div>`}).join(""):`<div class="empty-cart"><span>♡</span><h4>Your bag is taking a nap.</h4><p>Add something lovely for your pet.</p></div>`;
}
function openCart(){cartDrawer.classList.add("open");overlay.classList.add("show");cartDrawer.setAttribute("aria-hidden","false")}
function closeCart(){cartDrawer.classList.remove("open");overlay.classList.remove("show");cartDrawer.setAttribute("aria-hidden","true")}
let toastTimer;function showToast(msg){toast.textContent=msg;toast.classList.add("show");clearTimeout(toastTimer);toastTimer=setTimeout(()=>toast.classList.remove("show"),2200)}
productsEl.addEventListener("click",e=>{const b=e.target.closest("[data-add]");if(b)addToCart(Number(b.dataset.add))});
document.getElementById("categoryFilters").addEventListener("click",e=>{const b=e.target.closest("[data-category]");if(!b)return;activeCategory=b.dataset.category;document.querySelectorAll(".category-chip").forEach(x=>x.classList.toggle("active",x===b));renderProducts()});
document.getElementById("openCart").addEventListener("click",openCart);document.getElementById("closeCart").addEventListener("click",closeCart);overlay.addEventListener("click",closeCart);
document.getElementById("cartItems").addEventListener("click",e=>{const b=e.target.closest("[data-qty]");if(!b)return;const id=Number(b.dataset.qty),item=cart.find(x=>x.id===id);item.qty+=Number(b.dataset.change);if(item.qty<=0)cart=cart.filter(x=>x.id!==id);saveCart()});
const searchPanel=document.getElementById("searchPanel");document.querySelector(".search-toggle").addEventListener("click",()=>{searchPanel.classList.add("open");searchPanel.setAttribute("aria-hidden","false");document.getElementById("searchInput").focus()});document.getElementById("closeSearch").addEventListener("click",()=>{searchPanel.classList.remove("open");searchPanel.setAttribute("aria-hidden","true")});document.getElementById("searchInput").addEventListener("input",e=>{searchTerm=e.target.value.toLowerCase();renderProducts();document.getElementById("shop").scrollIntoView({behavior:"smooth"})});
document.getElementById("checkoutButton").addEventListener("click",()=>{if(!cart.length){showToast("Your bag is empty — add a favourite first.");return}document.getElementById("checkoutDialog").showModal()});
document.getElementById("checkoutForm").addEventListener("submit",e=>{e.preventDefault();const data=new FormData(e.currentTarget);const summary=cart.map(x=>{const p=products.find(p=>p.id===x.id);return `${p.name} x ${x.qty} — ${money(p.price*x.qty)}`}).join("\n");const total=cart.reduce((n,x)=>n+products.find(p=>p.id===x.id).price*x.qty,0);const msg=`Pawura demo order\nName: ${data.get("name")}\nPhone: ${data.get("phone")}\nAddress: ${data.get("address")}\nNote: ${data.get("note")}\n\n${summary}\nSubtotal: ${money(total)}\n\nPlease contact me to confirm availability and delivery.`;document.getElementById("checkoutDialog").close();alert("Thank you! This is a demo checkout. No order has been sent or payment taken.\n\nYour order summary is ready to copy:\n\n"+msg);});
document.getElementById("newsletterForm").addEventListener("submit",e=>{e.preventDefault();document.getElementById("newsletterMessage").textContent="Thank you! Newsletter signup is a visual demo and is not connected yet.";e.target.reset()});
document.getElementById("menuToggle").addEventListener("click",()=>{const nav=document.querySelector(".desktop-nav");nav.style.display=nav.style.display==="flex"?"none":"flex";nav.style.position="absolute";nav.style.top="68px";nav.style.left="0";nav.style.right="0";nav.style.padding="22px";nav.style.background="var(--paper)";nav.style.justifyContent="center";nav.style.zIndex="25"});
document.getElementById("year").textContent=new Date().getFullYear();renderProducts();renderCart();
