# Play Games Now — Android Application

## Description

Play Games Now Android is the mobile client of the Play Games Now digital game key store.

The application was developed in Kotlin using Jetpack Compose and communicates with the Laravel backend through a REST API.

The Android application does not access the MySQL database directly. All catalogue, authentication, cart, coupon, checkout, profile and purchase operations are performed through the Laravel API.

The Web application and Android application therefore share the same backend and database.

---

## Main Features

The Android application allows authenticated users to:

- Log in;
- Browse the game catalogue;
- Search for games;
- View game details;
- Add games to the shopping cart;
- Change product quantities;
- Remove products from the cart;
- Apply and remove coupons;
- View subtotal, discount and final total;
- Complete purchases;
- View purchase history;
- View purchased game keys;
- View applied coupons and discounts;
- Edit profile information;
- Change password;
- Log out.

---

## Architecture

The application follows an architecture based on **MVVM — Model-View-ViewModel**.

The main communication flow is:

```text
Jetpack Compose UI
        ↓
ViewModel
        ↓
Repository
        ↓
Retrofit
        ↓
Laravel REST API
        ↓
MySQL
```

The user interface is responsible for displaying data and user interaction.

ViewModels manage the state required by each screen.

Repositories handle communication between the ViewModels and the API.

Retrofit performs HTTP requests to the Laravel backend.

---

## Technologies

The mobile application uses:

- Kotlin;
- Jetpack Compose;
- MVVM;
- Retrofit;
- Coroutines;
- Laravel REST API;
- Laravel Sanctum authentication.

---

## Authentication

Users authenticate using their email and password.

The application sends the credentials to the Laravel API.

After a successful login, the server returns a Laravel Sanctum token.

The token is then included in protected API requests using Bearer authentication.

The authentication flow is therefore different from the Web application, which uses session-based authentication.

The Android application does not store or process Google OAuth credentials.

---

## Game Catalogue

The catalogue is obtained from the Laravel API.

Each sellable product represents a `GamePlatform`, combining a game with a specific platform.

The API provides information such as:

- Game title;
- Description;
- Image;
- Categories;
- Platform;
- Platform icon;
- Current price;
- Previous price;
- Available stock.

Game images can be loaded from either external URLs or Laravel storage URLs provided by the API.

---

## Game Details

The game detail screen displays information received from the backend for the selected GamePlatform.

The application does not calculate stock locally. Available stock is provided by the backend according to the number of available Game Keys.

---

## Shopping Cart

Cart operations are performed through the Laravel API.

The Android application can:

- Retrieve the active cart;
- Add products;
- Update quantities;
- Remove products.

Stock validation remains on the Laravel backend.

This prevents the mobile client from independently modifying quantities beyond the available stock.

---

## Coupons

The Android application supports discount coupons through dedicated API endpoints.

Users can enter a coupon code in the cart and apply it to the current purchase.

The backend validates:

- Whether the coupon exists;
- Whether it is active;
- Start and expiration dates;
- Usage limit.

Coupons can provide either a percentage discount or a fixed-value discount.

The Android application does not calculate the discount itself.

After a coupon is applied or removed, the application reloads the cart and displays the values returned by the API:

- Subtotal;
- Discount;
- Final total;
- Applied coupon.

This keeps the coupon logic centralized in the Laravel backend.

---

## Checkout

The mobile checkout sends the selected payment information to the Laravel API.

The backend is responsible for:

- Validating the request;
- Loading the active cart;
- Revalidating the coupon;
- Revalidating stock;
- Calculating the final values;
- Creating the Sale;
- Creating SaleItems;
- Assigning Game Keys;
- Marking Game Keys as sold;
- Converting the cart;
- Generating the purchase receipt;
- Sending the receipt by email.

Payment methods are simulated and no real payment gateway is currently integrated.

---

## Purchase Receipt

The Android application does not generate PDF files locally.

After a successful mobile purchase, the Laravel backend automatically generates a PDF purchase receipt using Dompdf and sends it to the email associated with the user's account.

The receipt contains the purchase information, including the game keys and any coupon discount applied.

This allows the same receipt system to be used for both Web and Android purchases.

---

## Purchase History

The Android application retrieves the authenticated user's sales through the API.

Users can open an order and view:

- Order number;
- Date;
- Status;
- Purchased games;
- Platforms;
- Quantities;
- Unit prices;
- Game keys;
- Coupon used;
- Discount;
- Final total.

---

## Profile

The profile area allows the authenticated user to view and update account information through the API.

The application supports updating:

- Name;
- Username;
- Email.

Users can also change their password.

The backend requires the current password before accepting a new password.

---

## API Communication

Retrofit is used to communicate with the Laravel REST API.

Protected requests require a Sanctum Bearer token.

Typical endpoints include operations related to:

```text
login
profile
game-platforms
cart-items
cart/coupon/apply
cart/coupon/remove
sales
logout
```

The exact endpoint configuration is defined in the Retrofit API service.

---

## Requirements

To run the Android application, the following are required:

- Android Studio;
- Android SDK;
- Android Emulator or physical Android device;
- Running Laravel backend;
- Access to the Play Games Now REST API.

---

## Backend Setup

Before starting the Android application, the Laravel backend must be running.

For development with the Android Emulator, start Laravel with:

```bash
php artisan serve --host=0.0.0.0 --port=8000
```

---

## API Base URL

When using the standard Android Emulator, `localhost` refers to the emulator itself.

To access a Laravel server running on the host computer, use:

```text
http://10.0.2.2:8000/api/
```

instead of:

```text
http://127.0.0.1:8000/api/
```

For a physical Android device, the computer's local network IP address must normally be used instead.

---

## Opening the Project

Open Android Studio and choose:

```text
Open
```

Select the Play Games Now Android project directory.

Allow Gradle to synchronize the dependencies.

Check that the API base URL points to the correct Laravel server before running the application.

---

## Running the Application

Start the Laravel backend first.

Then:

1. Start an Android Emulator or connect a physical device;
2. Open the project in Android Studio;
3. Run the application;
4. Log in using a valid Play Games Now customer account.

---

## Images

Game and platform images are returned by the API.

Laravel may return relative storage paths such as:

```text
/storage/games/example.jpg
```

The Android application builds the complete URL using the API host before loading the image.

External image URLs can be used directly.

---

## Error Handling

The application handles common API errors, including:

- Invalid authentication;
- Invalid coupon codes;
- Expired or unavailable coupons;
- Insufficient stock;
- Invalid checkout data;
- Failed profile updates;
- Unauthorized requests.

Business validation remains primarily on the backend so that the same rules are applied to both the Web and Android clients.

---

## Security Notes

The Android application must never contain server-side secrets such as:

- Gmail App Password;
- Google OAuth Client Secret;
- Database credentials;
- Laravel application key.

These credentials belong exclusively to the backend environment.

The mobile application should only store information required for client authentication, such as the Sanctum token.

---

## Relationship with the Web Application

The Android application and Web application are two different clients of the same Play Games Now system.

They share:

- Users;
- Games;
- Platforms;
- Categories;
- Stock;
- Coupons;
- Shopping carts;
- Sales;
- Game keys.

The main difference is the method used to access the backend.

The Web application communicates directly with Laravel through Web routes and sessions, while the Android application communicates through the REST API and uses token authentication.

Critical business logic remains centralized in Laravel.

---

## Current Limitations

Payments are simulated and no real payment provider is integrated.

Future improvements could include additional filters, more advanced notifications, enhanced reporting and integration with a real payment service.

---

## Author

Developed as the mobile component of the final project of the Técnico Especialista em Tecnologias e Programação de Sistemas de Informação course.

**Author:** João Francisco Lopes Marques Correia Mateus
**Class:** TPSI-CAS-0226  
**Year:** 2026