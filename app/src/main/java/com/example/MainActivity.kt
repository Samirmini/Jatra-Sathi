package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.BookingEntity
import com.example.data.TransactionEntity
import com.example.ui.JatraSathiUiState
import com.example.ui.JatraSathiViewModel
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = VelvetNavy
                ) {
                    EliteWebWrapper()
                }
            }
        }
    }
}

@Composable
fun EliteWebWrapper() {
    val viewModel: JatraSathiViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Ambient Moving Liquid Background Layer simulation
    val infiniteTransition = rememberInfiniteTransition(label = "ambient_liquid")
    val animScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF0F2042), Color(0xFF0A1128)),
                        center = Offset(size.width * 0.35f, size.height * 0.3f),
                        radius = size.minDimension * animScale
                    )
                )
            }
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val isDesktop = maxWidth > 840.dp
            val isTablet = maxWidth > 600.dp && maxWidth <= 840.dp

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            ) {
                // DESKTOP/TABLET LEFT BRAND PANEL
                if (isDesktop || isTablet) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(4.5f)
                            .padding(24.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Brand Logo and Title
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .background(LiquidEmeraldGlow, CircleShape)
                                        .border(2.dp, LiquidEmerald, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DirectionsCar,
                                        contentDescription = "JatraSathi Brand Icon",
                                        tint = LiquidEmerald,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "JatraSathi",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CreamSilk,
                                    letterSpacing = 0.5.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .background(LiquidEmerald, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "v2.0",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = VelvetNavy
                                    )
                                }
                            }
                            
                            Text(
                                text = "নির্ভরতায় পাশে সবসময়",
                                fontSize = 16.sp,
                                color = LiquidEmerald.copy(alpha = 0.9f),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Text(
                                text = "Elite Companion Escrow matching. Live track verified local guides, micro-insured transits, and cryptographically valid secure identities.",
                                fontSize = 14.sp,
                                color = TextSecondary,
                                lineHeight = 20.sp,
                                modifier = Modifier.padding(bottom = 24.dp)
                            )

                            // Verification Checklist Widget (Reflects Interactive Toggles dynamically in Realtime)
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                color = CardGlassColor,
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "SECURE IDENTITY COMPLIANCE",
                                        fontSize = 12.sp,
                                        color = LiquidEmerald,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp,
                                        modifier = Modifier.padding(bottom = 12.dp)
                                    )

                                    VerificationStepRow(
                                        title = "National NID Database Verified",
                                        description = "Biometric checkmark matching active.",
                                        checked = state.isNidUploaded
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    VerificationStepRow(
                                        title = "Face Recognition LiveMatch",
                                        description = "Cross-checked with national authority logs.",
                                        checked = state.isFaceMatchVerified
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    VerificationStepRow(
                                        title = "Automatic Transit Insurance",
                                        description = "Armed with SecureShield v2.1 protection.",
                                        checked = state.isInsuranceActive
                                    )
                                }
                            }
                        }

                        // Bottom status
                        Column(modifier = Modifier.padding(top = 16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(LiquidEmerald, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Supabase Realtime Channel: Active",
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Logged in: 00os000man0093817@gmail.com",
                                fontSize = 11.sp,
                                color = TextSecondary.copy(alpha = 0.7f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                // RIGHT CORE INTERACTIVE SHEET PANEL
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(5.5f)
                        .padding(if (isDesktop || isTablet) 16.dp else 0.dp),
                    shape = RoundedCornerShape(if (isDesktop || isTablet) 24.dp else 0.dp),
                    color = VelvetNavyLight,
                    border = if (isDesktop || isTablet) BorderStroke(1.dp, Color.White.copy(alpha = 0.12f)) else null,
                    tonalElevation = 4.dp
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Desktop/Tablet Tab Header vs Mobile App Header
                        if (!isDesktop && !isTablet) {
                            SmallAppBarMobile()
                        }

                        // Core View Container
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            when (state.activeTab) {
                                0 -> BookingEngineTab(viewModel, state)
                                1 -> ActiveRadarMapTab(viewModel, state)
                                2 -> TrustWalletHubTab(viewModel, state)
                            }
                        }

                        // Bottom Navigation Controller
                        BottomBarComponent(state.activeTab) { tabId ->
                            viewModel.setTab(tabId)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SmallAppBarMobile() {
    Surface(
        color = VelvetNavy,
        border = BorderStroke(0.dp, Color.Transparent),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(LiquidEmeraldGlow, CircleShape)
                            .border(1.dp, LiquidEmerald, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = "Logo",
                            tint = LiquidEmerald,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "JatraSathi Premium",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = CreamSilk
                    )
                }
                Box(
                    modifier = Modifier
                        .background(LiquidEmeraldGlow, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Verified",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LiquidEmerald
                    )
                }
            }
            Text(
                text = "নির্ভরতায় পাশে সবসময়",
                fontSize = 12.sp,
                color = LiquidEmerald.copy(alpha = 0.9f),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
fun VerificationStepRow(title: String, description: String, checked: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = if (checked) Icons.Default.Verified else Icons.Default.Warning,
            contentDescription = "Status icon",
            tint = if (checked) LiquidEmerald else AlertGold,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = CreamSilk
            )
            Text(
                text = description,
                fontSize = 11.sp,
                color = TextSecondary
            )
        }
    }
}

// ============== TAB 0: THE FLUID BOOKING ENGINE ==================
@Composable
fun BookingEngineTab(viewModel: JatraSathiViewModel, state: JatraSathiUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Companion Booking Engine",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = CreamSilk
        )

        // Stepper Visual Progress Dots
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StepBubble(step = 1, current = state.bookingStep + 1, text = "Route Setup")
            Spacer(modifier = Modifier.width(12.dp))
            Divider(modifier = Modifier.width(40.dp), color = if (state.bookingStep >= 1) LiquidEmerald else Color.Gray.copy(alpha = 0.3f), thickness = 2.dp)
            Spacer(modifier = Modifier.width(12.dp))
            StepBubble(step = 2, current = state.bookingStep + 1, text = "Preferences")
            Spacer(modifier = Modifier.width(12.dp))
            Divider(modifier = Modifier.width(40.dp), color = if (state.bookingStep >= 2) LiquidEmerald else Color.Gray.copy(alpha = 0.3f), thickness = 2.dp)
            Spacer(modifier = Modifier.width(12.dp))
            StepBubble(step = 3, current = state.bookingStep + 1, text = "Search Match")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Morphing content views
        when (state.bookingStep) {
            0 -> StepRouteSetup(viewModel, state)
            1 -> StepCompanionPreference(viewModel, state)
            2 -> StepMatchingVisualizer(viewModel, state)
        }
    }
}

@Composable
fun StepBubble(step: Int, current: Int, text: String) {
    val isActive = current >= step
    val isExact = current == step
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    if (isExact) LiquidEmerald else if (isActive) LiquidEmeraldGlow else Color.White.copy(alpha = 0.05f),
                    CircleShape
                )
                .border(
                    1.5.dp,
                    if (isActive) LiquidEmerald else Color.Gray.copy(alpha = 0.5f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = step.toString(),
                color = if (isExact) VelvetNavy else if (isActive) LiquidEmerald else CreamSilk,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = 10.sp,
            color = if (isActive) LiquidEmerald else TextSecondary,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun StepRouteSetup(viewModel: JatraSathiViewModel, state: JatraSathiUiState) {
    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Step 1: Set Destination Coordinates",
            fontSize = 15.sp,
            color = LiquidEmerald,
            fontWeight = FontWeight.Bold
        )

        // Route Inputs
        OutlinedTextField(
            value = state.pickupLocation,
            onValueChange = { viewModel.updatePickup(it) },
            label = { Text("Enter Pickup Address / Airport Node") },
            leadingIcon = { Icon(Icons.Default.LocationOn, "Pickup pin", tint = LiquidEmerald) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = CreamSilk,
                unfocusedTextColor = CreamSilk,
                focusedBorderColor = LiquidEmerald,
                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                unfocusedLabelColor = TextSecondary,
                focusedLabelColor = LiquidEmerald
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.destinationLocation,
            onValueChange = { viewModel.updateDestination(it) },
            label = { Text("Enter Final Destination") },
            leadingIcon = { Icon(Icons.Default.Directions, "Destination pin", tint = LiquidEmerald) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = CreamSilk,
                unfocusedTextColor = CreamSilk,
                focusedBorderColor = LiquidEmerald,
                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                unfocusedLabelColor = TextSecondary,
                focusedLabelColor = LiquidEmerald
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Preset shortcuts (To assist rapid AI testing and quick demonstration fill-ins)
        Text(
            text = "PRESET SAFEHAVEN SHORTCUTS — CLICK TO TEST",
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            color = LiquidEmerald.copy(alpha = 0.7f),
            letterSpacing = 0.5.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PresetRouteItem(pickup = "Hazrat Shahjalal Airport (DAC)", dest = "Gulshan-2 Circle Residence") { p, d ->
                viewModel.updatePickup(p)
                viewModel.updateDestination(d)
                Toast.makeText(context, "Filled preset destination routes", Toast.LENGTH_SHORT).show()
            }
            PresetRouteItem(pickup = "Dhaka Railway Terminal Node", dest = "Dhanmondi Lake Park Guarded Node") { p, d ->
                viewModel.updatePickup(p)
                viewModel.updateDestination(d)
                Toast.makeText(context, "Filled preset destination routes", Toast.LENGTH_SHORT).show()
            }
            PresetRouteItem(pickup = "Uttara Sector 4 Secure Hub", dest = "Motijheel Corporate Plaza") { p, d ->
                viewModel.updatePickup(p)
                viewModel.updateDestination(d)
                Toast.makeText(context, "Filled preset destination routes", Toast.LENGTH_SHORT).show()
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Companion Gender Preference Selection Chips
        Text(
            text = "Companion Gender Filter",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = CreamSilk
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val genders = listOf("Any", "Female", "Male")
            genders.forEach { gender ->
                val selected = state.preferredGender == gender
                Surface(
                    onClick = { viewModel.updateGender(gender) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (selected) LiquidEmerald else Color.White.copy(alpha = 0.05f),
                    border = BorderStroke(1.dp, if (selected) LiquidEmerald else Color.White.copy(alpha = 0.15f)),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier.padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = gender,
                            color = if (selected) VelvetNavy else CreamSilk,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.setBookingStep(1) },
            colors = ButtonDefaults.buttonColors(containerColor = LiquidEmerald, disabledContainerColor = Color.White.copy(alpha = 0.1f)),
            enabled = state.pickupLocation.isNotBlank() && state.destinationLocation.isNotBlank(),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text(
                text = "Continue to Companion Filters",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = VelvetNavy
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowForward, "Next step", tint = VelvetNavy)
        }
    }
}

@Composable
fun PresetRouteItem(pickup: String, dest: String, onSelect: (String, String) -> Unit) {
    Surface(
        onClick = { onSelect(pickup, dest) },
        shape = RoundedCornerShape(12.dp),
        color = CardGlassColor,
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f)),
        modifier = Modifier.width(220.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, "Origin", tint = LiquidEmerald, modifier = Modifier.size(13.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(pickup, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, color = CreamSilk, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Directions, "Destination", tint = SecureBlue, modifier = Modifier.size(13.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(dest, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, color = TextSecondary)
            }
        }
    }
}

@Composable
fun StepCompanionPreference(viewModel: JatraSathiViewModel, state: JatraSathiUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Step 2: Guardian Preference & Timing",
            fontSize = 15.sp,
            color = LiquidEmerald,
            fontWeight = FontWeight.Bold
        )

        // Language Filters
        Text(text = "Preferred Language Match", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = CreamSilk)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val languages = listOf("Bengali", "English", "Hindi")
            languages.forEach { lang ->
                val selected = state.preferredLanguage == lang
                FilterChip(
                    selected = selected,
                    onClick = { viewModel.updateLanguage(lang) },
                    label = { Text(lang, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        labelColor = CreamSilk,
                        selectedContainerColor = LiquidEmerald,
                        selectedLabelColor = VelvetNavy
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Timing Filters
        Text(text = "Travel Timing", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = CreamSilk)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val timings = listOf("Now", "In 15 Min", "In 45 Min")
            timings.forEach { time ->
                val selected = state.pickupTime == time
                FilterChip(
                    selected = selected,
                    onClick = { viewModel.updatePickupTime(time) },
                    label = { Text(time, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        labelColor = CreamSilk,
                        selectedContainerColor = LiquidEmerald,
                        selectedLabelColor = VelvetNavy
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Compliancy Notice
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White.copy(alpha = 0.03f),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.06f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Security, "Shield Info", tint = LiquidEmerald, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("JatraSathi Secure Escrow Shield", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CreamSilk)
                    Text(
                        "Your transaction funds (1,200 Coins) are safely locked in smart biometric escrow. Payment is released only when you tap 'Arrive Safely'.",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = { viewModel.setBookingStep(0) },
                border = BorderStroke(1.dp, LiquidEmerald),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = LiquidEmerald),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Text("Back", fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { viewModel.initiateBookingSimulation() },
                colors = ButtonDefaults.buttonColors(containerColor = LiquidEmerald),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.weight(1.5f).height(50.dp)
            ) {
                Text("Find Elite Companion", fontWeight = FontWeight.Bold, color = VelvetNavy)
            }
        }
    }
}

@Composable
fun StepMatchingVisualizer(viewModel: JatraSathiViewModel, state: JatraSathiUiState) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(160.dp)
        ) {
            // Rotating/pulsing matching circle animation
            val transition = rememberInfiniteTransition(label = "scanim")
            val rotAngle by transition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)),
                label = "rot"
            )
            val glowScale by transition.animateFloat(
                initialValue = 0.8f,
                targetValue = 1.15f,
                animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing), repeatMode = RepeatMode.Reverse),
                label = "glowScale"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        drawCircle(
                            color = LiquidEmerald.copy(alpha = 0.05f * glowScale),
                            radius = size.width / 2f
                        )
                        drawCircle(
                            color = LiquidEmerald,
                            radius = size.width / 2f,
                            style = Stroke(
                                width = 2f,
                                pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                                    floatArrayOf(15f, 15f),
                                    rotAngle
                                )
                            )
                        )
                    }
            )

            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = "Searching matches",
                tint = LiquidEmerald,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Querying Smart Escrow Matcher...",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = CreamSilk,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Broadcasting to active verified companions in ${state.pickupLocation}",
            fontSize = 12.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Ledger Integrity Check", fontSize = 12.sp, color = TextSecondary)
            Text("${(state.searchProgress * 100).toInt()}% Secure Linked", fontSize = 12.sp, color = LiquidEmerald, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { state.searchProgress },
            color = LiquidEmerald,
            trackColor = Color.White.copy(alpha = 0.08f),
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp))
        )
    }
}

// ============== TAB 1: THE ACTIVE RADAR PULSE MAP ==================
@Composable
fun ActiveRadarMapTab(viewModel: JatraSathiViewModel, state: JatraSathiUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Active-Sathi Live Tracking Portal",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = CreamSilk
        )

        if (state.activeBooking == null) {
            // Setup empty state of Tracking Radar portal
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White.copy(alpha = 0.02f),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.06f)),
                modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = "No active tracing",
                        tint = TextSecondary.copy(alpha = 0.3f),
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Active Travel Escrows",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = CreamSilk
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Initiate a travel companion search on the Booking Engine to trigger real-time coordinate streaming.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.setTab(0) },
                        colors = ButtonDefaults.buttonColors(containerColor = LiquidEmerald)
                    ) {
                        Text("Open Booking Engine", color = VelvetNavy, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            // Visual Active Radar Map Render
            RadarPulseMapCanvas(state)

            // Companion Detailed Specifications Widget
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardGlassColor,
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Avatar Placeholder
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .background(LiquidEmerald, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = state.activeBooking.companionName?.substring(0, 2)?.uppercase() ?: "JS",
                                    color = VelvetNavy,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = state.activeBooking.companionName ?: "Assigned Companion",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CreamSilk
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = "Supabase Verified Account",
                                        tint = LiquidEmerald,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Text(
                                    text = state.activeBooking.companionVehicle ?: "Secured Vehicle",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }
                        }

                        // Rating Badge
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.Star, "rating star", tint = AlertGold, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("4.9", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CreamSilk)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Divider(color = Color.White.copy(alpha = 0.08f))

                    Spacer(modifier = Modifier.height(12.dp))

                    // Location detail indicators
                    RouteIndicatorRow(label = "Pickup Coordinates Node", value = state.activeBooking.pickupLocation, color = LiquidEmerald)
                    Spacer(modifier = Modifier.height(8.dp))
                    RouteIndicatorRow(label = "Lock Destination Node", value = state.activeBooking.destinationLocation, color = SecureBlue)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("ESCROW VALUE", fontSize = 10.sp, color = TextSecondary, fontWeight = FontWeight.Bold)
                            Text("${state.activeBooking.priceCoins} Coins", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = LiquidEmerald)
                        }
                        Box(
                            modifier = Modifier
                                .background(
                                    if (state.activeBooking.status == "EN_ROUTE") AlertGold.copy(alpha = 0.15f) else LiquidEmeraldGlow,
                                    RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (state.activeBooking.status == "EN_ROUTE") "COMPANION EN-ROUTE" else "ACTIVE TRANSIT GOING",
                                color = if (state.activeBooking.status == "EN_ROUTE") AlertGold else LiquidEmerald,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Safe release, Cancel and SOS Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.cancelActiveJourney(state.activeBooking.id, state.activeBooking.priceCoins) },
                            border = BorderStroke(1.dp, AlertGold),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = AlertGold),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f).height(46.dp)
                        ) {
                            Text("Abort / Refund", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        Button(
                            onClick = { viewModel.completeActiveJourney(state.activeBooking.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = LiquidEmerald),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1.5f).height(46.dp)
                        ) {
                            Icon(Icons.Default.Verified, "Confirm Safe Release", tint = VelvetNavy, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Arrive Safe (Release Escrow)", fontWeight = FontWeight.Bold, color = VelvetNavy, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // SOS Trigger Button
                    Button(
                        onClick = { Toast.makeText(viewModel.getApplication(), "🚨 EMERGENCY ALERTS SENT INTO EXCLUSIVE DECENTRALIZED POLICE NODES", Toast.LENGTH_LONG).show() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(42.dp)
                    ) {
                        Text("EMERGENCY 999 SHIELD SOS", color = CreamSilk, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun RouteIndicatorRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "$label: ",
            fontSize = 12.sp,
            color = TextSecondary,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 12.sp,
            color = CreamSilk,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun RadarPulseMapCanvas(state: JatraSathiUiState) {
    // Glowing expanding radar waves animations using InfiniteTransitions
    val pulseTransition = rememberInfiniteTransition(label = "radar_sweep")
    val pulseRadius1 by pulseTransition.animateFloat(
        initialValue = 10f,
        targetValue = 280f,
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing)),
        label = "pulseRadius"
    )
    val pulseAlpha1 by pulseTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing)),
        label = "pulseAlpha"
    )

    val pulseRadius2 by pulseTransition.animateFloat(
        initialValue = 10f,
        targetValue = 280f,
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing, delayMillis = 2000)),
        label = "pulseRadius2"
    )
    val pulseAlpha2 by pulseTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing, delayMillis = 2000)),
        label = "pulseAlpha2"
    )

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.Black.copy(alpha = 0.4f),
        border = BorderStroke(1.dp, LiquidEmerald.copy(alpha = 0.25f)),
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val centerWidth = maxWidth / 2
            val centerHeight = maxHeight / 2

            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2f, size.height / 2f)

                // Concentric guide circles standard radar grid lines
                drawCircle(color = LiquidEmerald.copy(alpha = 0.1f), radius = 50f, center = center, style = Stroke(width = 1f))
                drawCircle(color = LiquidEmerald.copy(alpha = 0.08f), radius = 100f, center = center, style = Stroke(width = 1.5f))
                drawCircle(color = LiquidEmerald.copy(alpha = 0.06f), radius = 180f, center = center, style = Stroke(width = 1.5f))

                // X-Y Coordinate lines
                drawLine(
                    color = LiquidEmerald.copy(alpha = 0.05f),
                    start = Offset(0f, size.height / 2f),
                    end = Offset(size.width, size.height / 2f),
                    strokeWidth = 1f
                )
                drawLine(
                    color = LiquidEmerald.copy(alpha = 0.05f),
                    start = Offset(size.width / 2f, 0f),
                    end = Offset(size.width / 2f, size.height),
                    strokeWidth = 1f
                )

                // Expanding pulse emerald rings
                drawCircle(
                    color = LiquidEmerald.copy(alpha = pulseAlpha1 * 0.35f),
                    radius = pulseRadius1,
                    center = center,
                    style = Stroke(width = 2.5f)
                )
                drawCircle(
                    color = LiquidEmerald.copy(alpha = pulseAlpha2 * 0.35f),
                    radius = pulseRadius2,
                    center = center,
                    style = Stroke(width = 2.5f)
                )

                // Center node representing CLIENT (Self)
                drawCircle(
                    color = SecureBlue,
                    radius = 8f,
                    center = center
                )
                drawCircle(
                    color = SecureBlue.copy(alpha = 0.3f),
                    radius = 18f,
                    center = center,
                    style = Stroke(width = 2f)
                )

                // Companion tracking animated moving target node based on simulated loop state coordinates
                val scaleFactorLat = size.height * 4.5f
                val scaleFactorLon = size.width * 3.5f
                val targetMarkerOffset = Offset(
                    x = (size.width / 2f) + (state.companionLongitudeOffset * scaleFactorLon),
                    y = (size.height / 2f) + (state.companionLatitudeOffset * scaleFactorLat)
                )

                // Pulsing safety aura around companion node
                drawCircle(
                    color = LiquidEmerald.copy(alpha = 0.15f),
                    radius = 24f,
                    center = targetMarkerOffset
                )
                drawCircle(
                    color = LiquidEmerald,
                    radius = 7f,
                    center = targetMarkerOffset
                )
                drawCircle(
                    color = CreamSilk,
                    radius = 3f,
                    center = targetMarkerOffset
                )
            }

            // Client Floating "YOU" Tag Anchor
            Box(
                modifier = Modifier
                    .offset(x = centerWidth - 25.dp, y = centerHeight - 38.dp)
                    .background(SecureBlue, RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text("YOU", color = CreamSilk, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }

            // Companion Floating Animated Tag Anchor
            val factorLat = 220 * 4.5f
            val factorLon = 300 * 3.5f
            val cX = centerWidth - 30.dp + (state.companionLongitudeOffset * factorLon).dp
            val cY = centerHeight - 40.dp + (state.companionLatitudeOffset * factorLat).dp
            
            Box(
                modifier = Modifier
                    .offset(x = cX, y = cY)
                    .background(LiquidEmerald, RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = state.activeBooking?.companionName?.substringBefore(" ")?.uppercase() ?: "SATHI",
                    color = VelvetNavy,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}


// ============== TAB 2: THE TRUST & WALLET HUBS ==================
@Composable
fun TrustWalletHubTab(viewModel: JatraSathiViewModel, state: JatraSathiUiState) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Trust Ledger & Wallet Hub",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = CreamSilk
        )

        // Wallet Balance Glass Card
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White.copy(alpha = 0.04f),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawCircle(
                            color = LiquidEmerald.copy(alpha = 0.04f),
                            radius = size.width / 2f,
                            center = Offset(size.width, 0f)
                        )
                    }
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ESCROW CAPITAL TOKEN",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = LiquidEmerald,
                            letterSpacing = 1.sp
                        )
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Secured Vault",
                            tint = LiquidEmerald,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "${state.walletBalance} COINS",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = CreamSilk
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Cryptographic Ledger Status: SECURE SYNCHRONIZED",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DepositButton(amount = 1500, label = "+1,500 Coins") {
                            viewModel.depositFunds(1500)
                            Toast.makeText(context, "Added 1,500 coins to secure escrow", Toast.LENGTH_SHORT).show()
                        }
                        DepositButton(amount = 5000, label = "+5,000 Coins") {
                            viewModel.depositFunds(5000)
                            Toast.makeText(context, "Added 5,000 coins to secure escrow", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        // Compliancy interactive simulator knobs
        Text(
            text = "Compliance Testing Sandbox",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = CreamSilk
        )

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = CardGlassColor,
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ComplianceToggleRow(
                    title = "NID Verification Complete",
                    checked = state.isNidUploaded,
                    onCheckedChange = { viewModel.setNidUploaded(it) }
                )
                ComplianceToggleRow(
                    title = "Biometric Live Recognition Facial check",
                    checked = state.isFaceMatchVerified,
                    onCheckedChange = { viewModel.setFaceMatchVerified(it) }
                )
                ComplianceToggleRow(
                    title = "SecureShield Automatic micro-insurance active",
                    checked = state.isInsuranceActive,
                    onCheckedChange = { viewModel.setInsuranceActive(it) }
                )
            }
        }

        // Ledger Transactions List Title
        Text(
            text = "Encrypted Cryptographic Ledger Logs",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = CreamSilk
        )

        if (state.transactions.isEmpty()) {
            Text(
                text = "No recorded transactions in secure keys database.",
                fontSize = 12.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
            )
        } else {
            // Render beautiful Glass ledger log table entries
            state.transactions.forEach { txn ->
                LedgerRowItem(txn)
            }
        }
    }
}

@Composable
fun DepositButton(amount: Int, label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f)),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.12f)),
        modifier = Modifier.height(36.dp)
    ) {
        Text(text = label, color = CreamSilk, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ComplianceToggleRow(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = CreamSilk,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = VelvetNavy,
                checkedTrackColor = LiquidEmerald,
                uncheckedThumbColor = Color.LightGray,
                uncheckedTrackColor = Color.White.copy(alpha = 0.08f)
            )
        )
    }
}

@Composable
fun LedgerRowItem(txn: TransactionEntity) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.Black.copy(alpha = 0.2f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Ledger Verified Locks",
                    tint = LiquidEmerald,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = txn.type,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = when (txn.type) {
                            "DEPOSIT", "RECLAIM" -> LiquidEmerald
                            "PAYMENT" -> Color.White
                            else -> SecureBlue
                        }
                    )
                    Text(
                        text = txn.tokenCode,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Text(
                text = if (txn.type == "PAYMENT") "-${txn.amountCoins} Coins" else "+${txn.amountCoins} Coins",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (txn.type == "PAYMENT") AlertGold else LiquidEmerald
            )
        }
    }
}

// ============== CUSTOM SHARED UI: THE NAV CONTROLLER ==================
@Composable
fun BottomBarComponent(activeTab: Int, onTabSelect: (Int) -> Unit) {
    Surface(
        color = VelvetNavy,
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f)),
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Default.DirectionsCar,
                label = "Booking Engine",
                selected = activeTab == 0,
                onClick = { onTabSelect(0) }
            )
            BottomNavItem(
                icon = Icons.Default.Directions,
                label = "Pulse Radar",
                selected = activeTab == 1,
                onClick = { onTabSelect(1) }
            )
            BottomNavItem(
                icon = Icons.Default.AccountBalanceWallet,
                label = "Trust Wallet",
                selected = activeTab == 2,
                onClick = { onTabSelect(2) }
            )
        }
    }
}

@Composable
fun BottomNavItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) LiquidEmerald else TextSecondary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) LiquidEmerald else TextSecondary
        )
    }
}
