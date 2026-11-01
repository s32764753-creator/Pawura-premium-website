package com.pawura.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Forest = Color(0xFF244F42)
private val Cream = Color(0xFFF5F5EC)
private val Sage = Color(0xFFDCE8D7)
private val Muted = Color(0xFF657168)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PawuraApp() }
    }
}

@Composable
fun PawuraApp() {
    var selectedTab by remember { mutableStateOf("Home") }
    var showLogin by remember { mutableStateOf(false) }
    var selectedPet by remember { mutableStateOf("") }

    MaterialTheme(colorScheme = lightColorScheme(
        primary = Forest, background = Cream, surface = Color.White,
        onPrimary = Color.White, onBackground = Forest, onSurface = Forest
    )) {
        Box(Modifier.fillMaxSize().background(Cream)) {
            Column(Modifier.fillMaxSize()) {
                Row(
                    Modifier.fillMaxWidth().padding(start = 22.dp, end = 20.dp, top = 18.dp, bottom = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.size(54.dp).clip(RoundedCornerShape(17.dp)).background(Forest), contentAlignment = Alignment.Center) {
                        Text("🐾", fontSize = 28.sp, color = Color.White)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("pawura", fontSize = 29.sp, fontWeight = FontWeight.Bold, color = Forest, lineHeight = 31.sp)
                        Text("Happy pets, delivered.", fontSize = 13.sp, color = Muted)
                    }
                    TextButton(onClick = { showLogin = true }) {
                        Text("Войти", fontSize = 16.sp, color = Color(0xFF242824))
                    }
                }

                when (selectedTab) {
                    "Home" -> LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        item {
                            Column(
                                Modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp)).background(Sage).padding(18.dp)
                            ) {
                                Text("GOOD FOOD. HAPPY PAWS.", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Forest)
                                Spacer(Modifier.height(8.dp))
                                Text("Their favorites,\\nat your door.".replace("\\n", "\n"), fontSize = 36.sp, lineHeight = 39.sp, fontWeight = FontWeight.ExtraBold, color = Forest)
                                Spacer(Modifier.height(8.dp))
                                Text("Shop food and treats for your furry friends.", fontSize = 15.sp, color = Muted, lineHeight = 20.sp)
                                Spacer(Modifier.height(12.dp))
                                Image(
                                    painter = painterResource(R.drawable.hero_pets),
                                    contentDescription = "A happy dog and cat beside a food bowl",
                                    modifier = Modifier.fillMaxWidth().height(190.dp).clip(RoundedCornerShape(15.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        item {
                            Text("Shop by pet", fontSize = 21.sp, fontWeight = FontWeight.Bold, color = Forest)
                        }
                        item {
                            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                                PetCard("Dog food", "Meals and treats", R.drawable.dog_food, Modifier.weight(1f)) { selectedPet = "Dog food" }
                                PetCard("Cat food", "Daily nutrition", R.drawable.cat_food, Modifier.weight(1f)) { selectedPet = "Cat food" }
                            }
                        }
                        if (selectedPet.isNotBlank()) {
                            item {
                                Row(
                                    Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Sage).padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(Modifier.weight(1f)) {
                                        Text(selectedPet, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                        Text("Browse tasty picks for your pet.", color = Muted, fontSize = 13.sp)
                                    }
                                    TextButton(onClick = { selectedTab = "Explore" }) { Text("Explore") }
                                }
                            }
                        }
                    }
                    "Explore" -> SimplePage("Explore", "Find food, treats and everyday essentials for happy pets.")
                    "Cart" -> SimplePage("Your cart", "Your bowl is empty for now. Add your pet’s favorites from Explore.")
                    else -> SimplePage("Profile", "Sign in to manage your account and orders.")
                }

                NavigationBar(
                    containerColor = Color.White,
                    contentColor = Forest,
                    tonalElevation = 0.dp
                ) {
                    NavItem("Home", selectedTab, Icons.Filled.Home) { selectedTab = "Home" }
                    NavItem("Explore", selectedTab, Icons.Filled.Search) { selectedTab = "Explore" }
                    NavItem("Cart", selectedTab, Icons.Filled.ShoppingCart) { selectedTab = "Cart" }
                    NavItem("Profile", selectedTab, Icons.Filled.PersonOutline) { selectedTab = "Profile" }
                }
            }
        }
        if (showLogin) {
            AlertDialog(
                onDismissRequest = { showLogin = false },
                title = { Text("Welcome to Pawura") },
                text = { Text("Sign-in will be connected when you add your authentication service.") },
                confirmButton = { TextButton(onClick = { showLogin = false }) { Text("Got it") } }
            )
        }
    }
}

@Composable
private fun PetCard(title: String, subtitle: String, image: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier.clip(RoundedCornerShape(20.dp)).background(Color.White).clickable(onClick = onClick).padding(12.dp)
    ) {
        Image(
            painter = painterResource(image), contentDescription = title,
            modifier = Modifier.fillMaxWidth().height(112.dp).clip(RoundedCornerShape(14.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(12.dp))
        Text(title, fontSize = 19.sp, fontWeight = FontWeight.Bold, color = Forest)
        Spacer(Modifier.height(7.dp))
        Text(subtitle, fontSize = 13.sp, color = Muted)
        Spacer(Modifier.height(5.dp))
    }
}

@Composable
private fun NavItem(label: String, selected: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    NavigationBarItem(
        selected = selected == label,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label, modifier = Modifier.size(23.dp)) },
        label = { Text(label, fontSize = 12.sp) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Forest, selectedTextColor = Forest,
            unselectedIconColor = Color(0xFF7E8880), unselectedTextColor = Color(0xFF7E8880),
            indicatorColor = Sage
        )
    )
}

@Composable
private fun SimplePage(title: String, message: String) {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Forest)
        Spacer(Modifier.height(10.dp))
        Text(message, fontSize = 16.sp, color = Muted, lineHeight = 24.sp)
    }
}
