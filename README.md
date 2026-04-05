Hotel Management System
A comprehensive Hotel Management System built using Java and JavaFX to automate hotel operations such as booking, room management, billing, feedback analysis, and revenue tracking.
This project demonstrates strong concepts in database management, UI design, and real-world business logic implementation.

User Roles & Authentication
The system includes a secure login mechanism with two roles:
Admin
Staff
Each role has restricted access:
🔑 Admin → Full access (including revenue analytics)
👨‍💼 Staff → All features except revenue dashboard

Dashboard (Welcome Page)
After login, users are greeted with a dynamic dashboard displaying:
🏠 Total number of rooms
🛏️ Booked rooms
💰 Total revenue
📈 Live system statistics

Room Booking System 
Users can book rooms by providing:
Room type
Number of days
Number of kids
🧠 Smart Logic Implemented:
If kids = 1 → Prompt for extra mattress (optional with charge)
If kids ≥ 2 → Extra mattress added compulsorily
📶 WiFi option (checkbox)
🎟️ Membership discount (10% if applicable)

Checkout & Billing System
Generates final invoice based on:
Room type
Duration
Extra services
If user is not a member:
Prompt to join membership
Collects user details
Applies benefits if enrolled

Feedback System (FeedbackView)
Users can submit feedback
Feedback is stored in tabular format
🎨 Sentiment Highlight Feature:
Positive words (good, great, amazing) → 🟢 Green
Negative words (bad, poor, slow) → 🔴 Red
This provides a visual sentiment analysis effect.

Admin Revenue View
(Admin only feature)
💰 Total revenue of the day
📋 Detailed customer records
📊 Graph showing:
Most preferred room type
👉 Helps in business decision-making & hotel renovations

🛠️ Tech Stack
Frontend: JavaFX
Backend: Java

⚙️ How to Run
Clone the repository:
git clone https://github.com/codewithtrisha09/hotel-management-system.git
Open in IntelliJ / Eclipse
Create required tables
Run the main application file

🚀 Future Enhancements
Online booking integration
Payment gateway
AI-based feedback analysis
Email notifications
Multi-hotel support

⭐ Project Highlights
✔ Role-based authentication
✔ Smart booking logic
✔ Sentiment-based feedback coloring
✔ Revenue analytics with graphs
✔ Real-world hotel workflow implementation

👩‍💻 Author
Trisha (codewithtrisha09)
GitHub: https://github.com/codewithtrisha09

