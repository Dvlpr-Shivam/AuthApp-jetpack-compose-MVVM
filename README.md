# 🔐 Android Auth App – Login & Sign-Up with OTP

A modern Android app built with **Jetpack Compose**, **MVVM**, **Hilt**, and **StateFlow** that provides:

- 📲 **Login** with Email/Phone using OTP
- 📝 **Sign-Up** with business and contact details
- 🔁 OTP Verification with Countdown and Resend
- ✅ Field Validation (email, phone, password, etc.)
- 📍 Google Places integration for Location input

---

## 📱 Features

### 🚪 Login Screen
- Enter **email or phone And Password**
- Send and verify **OTP**
- 30s countdown timer and **Resend OTP** feature

### 📝 Sign-Up Screen
- **Business Details**:
    - Business Name, Email, Address, Phone
    - Location via Google Maps or Places API
    - Customer Count (Slider)
- **Contact Person Details**:
    - Name, Email, Phone, Designation
    - Date of Birth (Date Picker)
- **Authentication**:
    - Set Password (with rules)

### 📍 Location Picker
- Tap to pick from Google Map
- OR search by name using Places Autocomplete

---

## 🛠️ Tech Stack

| Layer           | Technology                |
|----------------|---------------------------|
| UI             | Jetpack Compose           |
| Architecture   | MVVM                      |
| DI             | Hilt                      |
| State Mgmt     | StateFlow + ViewModel     |
| Location       | Google Maps & Places API  |
| Testing        | JUnit, Compose UI Testing |

---

## 🚀 Getting Started

### 🔧 Prerequisites

- Android Studio Giraffe or higher
