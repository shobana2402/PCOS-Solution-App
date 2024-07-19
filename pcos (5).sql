-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 25, 2024 at 05:56 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pcos`
--

-- --------------------------------------------------------

--
-- Table structure for table `Admin`
--

CREATE TABLE `Admin` (
  `id` int(11) NOT NULL,
  `Name` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `email` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Admin`
--

INSERT INTO `Admin` (`id`, `Name`, `password`, `email`) VALUES
(1, 'Vidhya', '12345678', 'vidhya@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `calender`
--

CREATE TABLE `calender` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `calendar_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `calender`
--

INSERT INTO `calender` (`id`, `name`, `calendar_date`) VALUES
(1, 'shobana', '2024-01-25'),
(2, 'shobana', '2024-01-26'),
(3, 'shobana', '2024-01-27'),
(24, 'shobana', '2024-03-06'),
(25, 'shobana', '2024-03-07'),
(40, 'shobana', '2024-03-08'),
(41, 'shobana', '2024-02-29'),
(42, 'shobana', '2024-03-13'),
(43, 'shobana', '2024-03-14'),
(60, 'isha', '2024-03-22'),
(61, 'isha', '2024-03-21'),
(62, 'isha', '2024-03-20'),
(63, 'isha', '2024-03-19'),
(64, 'isha', '2024-03-18'),
(65, 'isha', '2024-03-17'),
(66, 'isha', '2024-03-10'),
(67, 'isha', '2024-03-11'),
(68, 'isha', '2024-03-12'),
(69, 'isha', '2024-03-13'),
(70, 'isha', '2024-03-14'),
(71, 'isha', '2024-03-15'),
(72, 'isha', '2024-03-16'),
(73, 'isha', '2024-03-09'),
(74, 'isha', '2024-03-08'),
(75, 'isha', '2024-03-07'),
(76, 'isha', '2024-03-05'),
(77, 'isha', '2024-03-04'),
(78, 'isha', '2024-03-03'),
(79, 'isha', '2024-03-06'),
(80, 'shobana', '2024-03-15'),
(81, 'shobana', '2024-03-12'),
(82, 'shobana', '2024-04-19'),
(83, 'shobana', '2024-04-20'),
(84, 'shobana', '2024-04-18'),
(85, 'shobana', '2024-04-17'),
(86, 'divya', '2024-04-11'),
(87, 'divya', '2024-04-12'),
(88, 'shradha', '2024-04-03'),
(89, 'shradha', '2024-04-17'),
(90, 'shradha', '2024-04-18');

-- --------------------------------------------------------

--
-- Table structure for table `daily_steps`
--

CREATE TABLE `daily_steps` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `day` int(11) NOT NULL,
  `steps_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `daily_steps`
--

INSERT INTO `daily_steps` (`id`, `name`, `day`, `steps_count`) VALUES
(1, 'shobana', 1, 3500),
(8, 'shobana', 2, 5000),
(9, 'shobana', 3, 6000),
(10, 'shobana', 4, 3000),
(12, 'thara', 1, 6000),
(13, 'shobana', 5, 3000),
(14, 'sana', 1, 5000),
(16, 'shobana', 25, 7373),
(17, 'shinny', 1, 9000),
(18, 'shradha', 1, 9000);

-- --------------------------------------------------------

--
-- Table structure for table `doctor_login`
--

CREATE TABLE `doctor_login` (
  `id` int(11) NOT NULL,
  `doctor_id` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  `email` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctor_login`
--

INSERT INTO `doctor_login` (`id`, `doctor_id`, `password`, `email`) VALUES
(1, 'D10001', '123D123', 'roslin@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `food`
--

CREATE TABLE `food` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `calorie` double DEFAULT NULL,
  `fat` double DEFAULT NULL,
  `pro` double DEFAULT NULL,
  `carb` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `food`
--

INSERT INTO `food` (`id`, `name`, `calorie`, `fat`, `pro`, `carb`) VALUES
(112, 'shobana', 170, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `food_calorie`
--

CREATE TABLE `food_calorie` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `breakfast` int(11) DEFAULT NULL,
  `lunch` int(11) DEFAULT NULL,
  `dinner` int(11) DEFAULT NULL,
  `total_calorie` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `food_calorie`
--

INSERT INTO `food_calorie` (`id`, `name`, `breakfast`, `lunch`, `dinner`, `total_calorie`) VALUES
(1, 'thara', 400, 400, NULL, NULL),
(2, 'shobana', 470, NULL, NULL, 470),
(3, 'riya', 275, NULL, NULL, 275),
(4, 'shinny', NULL, 295, NULL, 295),
(5, 'shradha', 344, NULL, NULL, 344);

-- --------------------------------------------------------

--
-- Table structure for table `forgotpassword`
--

CREATE TABLE `forgotpassword` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `newpassword` varchar(255) NOT NULL,
  `confirmpassword` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `forgotpassword`
--

INSERT INTO `forgotpassword` (`id`, `username`, `email`, `newpassword`, `confirmpassword`) VALUES
(22, 'roslin', 'roslin@gmail.com', 'rose', 'rose'),
(25, 'roslin', 'roslin@gmail.com', '12345678', '12345678');

-- --------------------------------------------------------

--
-- Table structure for table `leaderboard`
--

CREATE TABLE `leaderboard` (
  `Name` varchar(50) NOT NULL,
  `Totalscore` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `leaderboard`
--

INSERT INTO `leaderboard` (`Name`, `Totalscore`) VALUES
('riya', 6340),
('shobana', 28140),
('shradha', 9389);

-- --------------------------------------------------------

--
-- Table structure for table `mcq_questions`
--

CREATE TABLE `mcq_questions` (
  `question_id` int(11) NOT NULL,
  `question` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mcq_questions`
--

INSERT INTO `mcq_questions` (`question_id`, `question`) VALUES
(1, 'Feeling lack of control over the situation with periods'),
(2, 'Having difficulties staying at your ideal weight?'),
(3, 'Growth of visible hair?'),
(4, 'Having Irregular Menstural period?'),
(5, 'Having Abdominal Bloating?'),
(6, 'Worried About Having PCOS?'),
(7, 'Had Trouble dealing with your Weight?'),
(8, 'Felt Frustration in trying to loss Weight'),
(9, 'Had Low Self Esteem as a result of Having PCOS?'),
(10, 'worried about your health and feeling weird?'),
(11, 'Feel Sad because of Infertile Problems?'),
(12, 'Embarrassment about excessive hair?'),
(13, 'Self Conscious as a result of having PCOS?'),
(14, 'Late Menstrual Period?'),
(15, 'Felt Afraid of not being able to have children?');

-- --------------------------------------------------------

--
-- Table structure for table `mcq_questions_options`
--

CREATE TABLE `mcq_questions_options` (
  `question_id` int(11) DEFAULT NULL,
  `option_text` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mcq_questions_options`
--

INSERT INTO `mcq_questions_options` (`question_id`, `option_text`) VALUES
(1, 'All of the time'),
(1, 'Most of the time'),
(1, 'A Good bit of the time'),
(1, 'Some of the time'),
(1, 'A little of the time'),
(1, 'Hardly any of the time'),
(1, 'None of the time'),
(4, 'Severe Problem'),
(4, 'A major Problem'),
(4, 'A Moderate Problem'),
(4, 'Some Problem'),
(4, 'A little Problem'),
(4, 'Hardly any Problem'),
(4, 'No problem'),
(3, 'Severe Problem'),
(3, 'A major Problem'),
(3, 'A Moderate Problem'),
(3, 'Some Problem'),
(3, 'A little Problem'),
(3, 'Hardly any Problem'),
(3, 'No problem'),
(2, 'All of the Time'),
(2, 'Most of the Time'),
(2, 'A Good bit of the Time'),
(2, 'Some of the Time'),
(2, 'A Little of the Time'),
(2, 'Hardly any any of the Time'),
(2, 'None of the Time'),
(5, 'Severe Problem'),
(5, 'A major Problem'),
(5, 'A Moderate Problem'),
(5, 'Some Problem'),
(5, 'A little Problem'),
(5, 'Hardly any Problem'),
(5, 'No problem'),
(6, 'All of the Time'),
(6, 'Most of the Time'),
(6, 'A good bit of the Time'),
(6, 'Some of the Time'),
(6, 'A little of the Time'),
(6, 'Hardly Any of the Time'),
(6, 'None of the Time'),
(7, 'All of the Time'),
(7, 'Most of the Time'),
(7, 'A good bit of the Time'),
(7, 'Some of the Time'),
(7, 'A little of the Time'),
(7, 'Hardly Any of the Time'),
(7, 'None of the Time'),
(8, 'All of the time'),
(8, 'Most of the time'),
(8, 'A Good bit of the time'),
(8, 'Some of the time'),
(8, 'A little of the time'),
(8, 'Hardly any of the time'),
(8, 'None of the time'),
(9, 'All of the time'),
(9, 'Most of the time'),
(9, 'A Good bit of the time'),
(9, 'Some of the time'),
(9, 'A little of the time'),
(9, 'Hardly any of the time'),
(9, 'None of the time'),
(10, 'All of the time'),
(10, 'Most of the time'),
(10, 'A Good bit of the time'),
(10, 'Some of the time'),
(10, 'A little of the time'),
(10, 'Hardly any of the time'),
(10, 'None of the time'),
(11, 'All of the Time'),
(11, 'Most Of the Time'),
(11, 'A Good bit of the time'),
(11, 'Some Of the Time'),
(11, 'A Little of the time'),
(11, 'Hardly any of the Time'),
(11, 'None of the Time'),
(12, 'A Severe Problem'),
(12, 'A Major Problem'),
(12, 'A Moderate Problem'),
(12, 'Some Problem'),
(12, 'A Little Problem'),
(12, 'Hardly Any Problem'),
(12, 'No Problem'),
(13, 'All of the time'),
(13, 'Most of the time'),
(13, 'A good bit  of the time'),
(13, 'some of the time'),
(13, 'A little of time'),
(13, 'Hardly of the time'),
(13, 'None of the time'),
(14, 'A Severe Problem'),
(14, 'A Major Problem'),
(14, 'A Moderate Problem'),
(14, 'Some Problem'),
(14, 'A Little Problem'),
(14, 'Hardly any Problem'),
(14, 'No Problem'),
(15, 'All of the time'),
(15, 'Most of the time'),
(15, 'A Good of the time'),
(15, 'Some of the time'),
(15, 'A Little of the time'),
(15, 'Hardly any time'),
(15, 'None of the time');

-- --------------------------------------------------------

--
-- Table structure for table `medical_record`
--

CREATE TABLE `medical_record` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `record_path` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medical_record`
--

INSERT INTO `medical_record` (`id`, `name`, `record_path`) VALUES
(3, 'thara', 'uploads/pcos_report.png'),
(4, 'shobana', 'uploads/report.png'),
(5, 'thara', 'uploads/report.png'),
(6, 'shobana', 'uploads/pcos_report.png'),
(7, 'shobana', 'uploads/pcos_report.png'),
(17, 'shradha', 'uploads/image.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `meditation`
--

CREATE TABLE `meditation` (
  `id` int(11) NOT NULL,
  `audio_name` varchar(255) NOT NULL,
  `audio_path` varchar(255) NOT NULL,
  `audio_description` text DEFAULT NULL,
  `category` varchar(255) NOT NULL,
  `duration` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `meditation`
--

INSERT INTO `meditation` (`id`, `audio_name`, `audio_path`, `audio_description`, `category`, `duration`) VALUES
(5, 'Better sleep', 'uploads/better sleep.mp4', 'Sleep music helps establish a soothing baseline of sound without too much variation, to help you fall asleep — and keep you asleep. It masks other sounds, inside or outside. In effect, the music becomes the pillow we rest our head on. This is what healthy sleep sounds like!', 'Better sleep', '10 mins'),
(8, 'personal growth', 'uploads/personal.mp4', 'listen to the motivating music helps to improve your personal growth. A good motivational music is something elevates a person\'s feeling basically from sadness to happiness. It also changes from person to person my motivational music may not work for you.', 'personal growth', '20 mins'),
(9, 'Reduce stress', 'uploads/stress.mp4', 'Reduce your stress and depression by listening to this stress free music and Enjoy!. With benefits for mental and physical wellbeing, music can cause “feel-good” chemicals like dopamine to be released in the brain, helping to boost your mood and shift your emotional state. That\'s why music can be so effective for stress-relief.', 'Reduce stress', '4 mins'),
(10, 'Reduce Anxiety', 'uploads/reduce.mp4', 'Anxiety is a feeling of fear, dread, and uneasiness. It might cause you to sweat, feel restless and tense, and have a rapid heartbeat. It can be a normal reaction to stress. For example, you might feel anxious when faced with a difficult problem at work, before taking a test, or before making an important decision.', 'Reduce Anxiety', '3 mins'),
(11, 'increase happiness', 'uploads/happiness.mp4', 'Faster music tends to induce more positive emotions than slower music. Research suggests that music that is perceived as happy is usually performed at a tempo between 140 and 150 beats per minute (BPM). Songs people have said they use to improve their mood include Queen\'s Don\'t Stop.', 'increase happiness', '3 mins'),
(12, 'Better Concentration', 'uploads/concentration.mp4', 'The absence of words in the music may be one factor, as songs that contain lyrics have been found to be a distraction when you\'re trying to focus. And classical music is known for being calming, relaxing and helping reduce stress.', 'Better Concentration', '4 mins');

-- --------------------------------------------------------

--
-- Table structure for table `personal_details`
--

CREATE TABLE `personal_details` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `Mobile_No` varchar(10) DEFAULT NULL,
  `height` decimal(5,2) DEFAULT NULL,
  `weight` decimal(5,2) DEFAULT NULL,
  `bmi` decimal(5,2) DEFAULT NULL,
  `otherdisease` varchar(255) DEFAULT NULL,
  `obstetricscore` varchar(100) DEFAULT NULL,
  `hip` decimal(5,2) DEFAULT NULL,
  `waist` decimal(5,2) DEFAULT NULL,
  `hipwaist` decimal(5,2) DEFAULT NULL,
  `profile_image` varchar(1024) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `personal_details`
--

INSERT INTO `personal_details` (`id`, `name`, `age`, `Mobile_No`, `height`, `weight`, `bmi`, `otherdisease`, `obstetricscore`, `hip`, `waist`, `hipwaist`, `profile_image`) VALUES
(37, 'divya', 21, '9632587410', 156.00, 60.00, 24.65, 'no', 'Unmarried', 80.00, 100.00, 1.25, NULL),
(39, 'shobana', 21, '9632587410', 156.00, 44.00, 18.49, 'no', 'Unmarried', 80.00, 100.00, 1.25, ''),
(40, 'riya', 26, '9632587410', 156.00, 65.00, 26.71, 'no', 'Two Child', 80.00, 100.00, 1.25, NULL),
(56, 'shradha', 28, '7896325422', 156.00, 65.00, 26.71, 'No', 'Two Child', 80.00, 100.00, 1.25, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `score`
--

CREATE TABLE `score` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `mild` double NOT NULL,
  `moderate` double NOT NULL,
  `severe` double NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `next_assessment_date` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `score`
--

INSERT INTO `score` (`id`, `name`, `mild`, `moderate`, `severe`, `created_date`, `next_assessment_date`) VALUES
(2, 'shobana', 7, 8, 0, '2024-04-09 08:29:16', '2024-04-23'),
(7, 'thara', 2, 6, 2, '2024-03-18 08:12:21', '2024-04-01'),
(12, 'divya', 7, 8, 0, '2024-04-08 05:48:06', '2024-04-22'),
(13, 'nisha', 2, 9, 4, '2024-03-30 03:21:38', '2024-04-13'),
(14, 'isha', 6, 9, 0, '2024-04-08 06:14:14', '2024-04-22'),
(15, 'kathir', 9, 5, 1, '2024-04-01 09:31:19', '2024-04-15'),
(18, 'sahithi', 4, 9, 2, '2024-04-01 08:53:50', '2024-04-15'),
(19, 'rea', 3, 8, 4, '2024-04-05 10:07:54', '2024-04-19'),
(20, 'riya', 4, 9, 2, '2024-04-09 09:23:54', '2024-04-23'),
(21, 'shradha', 8, 6, 1, '2024-04-16 06:17:50', '2024-04-30');

-- --------------------------------------------------------

--
-- Table structure for table `signup`
--

CREATE TABLE `signup` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `confirm_password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `signup`
--

INSERT INTO `signup` (`id`, `username`, `email`, `password`, `confirm_password`) VALUES
(19, 'shobana', 'shobana@gmail.com', '12345678', '12345678'),
(69, 'divya', 'divya@gmail.com', '12345678', '12345678'),
(71, 'riya', 'riya@gmail.com', '12345678', '12345678'),
(73, 'shradha', 'sara@gmail.com', '12345678', '12345678');

-- --------------------------------------------------------

--
-- Table structure for table `today_progress`
--

CREATE TABLE `today_progress` (
  `id` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `calories_taken` int(100) DEFAULT NULL,
  `exercise_duration` int(11) DEFAULT NULL,
  `todays_feedback` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `no_of_steps` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `today_progress`
--

INSERT INTO `today_progress` (`id`, `day`, `calories_taken`, `exercise_duration`, `todays_feedback`, `score`, `no_of_steps`, `name`, `Date`) VALUES
(35, 1, 470, 45, 8, 6515, 6000, 'shobana', '2024-04-09'),
(36, 2, 200, 45, 8, 10245, 10000, 'shobana', '2024-04-10'),
(37, 3, 200, 45, 8, 10245, 10000, 'shobana', '2024-04-13'),
(38, 4, 470, 65, 8, 1135, 600, 'shobana', '2024-04-20'),
(39, 1, 275, 65, 5, 6340, 6000, 'riya', '2024-04-09'),
(40, 1, 295, 45, 9, 9340, 9000, 'shinny', '2024-04-12'),
(41, 1, 344, 45, 7, 9389, 9000, 'shradha', '2024-04-16');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` enum('patient','doctor') NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `role`, `created_on`) VALUES
(32, 'shobana', '12345678', 'shobana@gmail.com', 'patient', '2023-12-15 07:03:05'),
(56, 'roslin', '12345678', 'roslin@gmail.com', 'doctor', '2024-01-25 04:22:59'),
(81, 'divya', '12345678', 'divya@gmail.com', 'patient', '2024-04-08 05:47:00'),
(83, 'riya', '12345678', 'riya@gmail.com', 'patient', '2024-04-09 09:22:51'),
(90, 'shradha', '12345678', 'sara@gmail.com', 'patient', '2024-04-16 06:16:46');

-- --------------------------------------------------------

--
-- Table structure for table `videos`
--

CREATE TABLE `videos` (
  `exercise_name` varchar(255) NOT NULL,
  `video_description` text DEFAULT NULL,
  `video_url` varchar(255) DEFAULT NULL,
  `duration` text DEFAULT NULL,
  `process` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `videos`
--

INSERT INTO `videos` (`exercise_name`, `video_description`, `video_url`, `duration`, `process`) VALUES
('Adductor Stretch in Standing', 'Instructions: Stand with feet wider than shoulder-width apart. Shift your weight to one side and lean towards that side, feeling a stretch in the inner thigh of the opposite leg.\r\nMuscles: Stretches the adductor muscles of the inner thigh.', 'exercise/Adductor stretch in standing.mp4', '20 secs', '5 reps'),
('Arm Scissors ', ' Instructions: Stand with arms extended out to the sides. Cross one arm over the other, then quickly switch the crossing motion back and forth.\r\n   - Muscles: Works the shoulder muscles and engages the chest and back muscles.', 'exercise/Arm scissors.mp4', '5 mins', '30 seconds each'),
('Assisted Lunges', 'Stand with your feet hip-width apart.\r\n   - Take a step forward with one leg and lower your body until both knees are bent at a 90-degree angle.\r\n   - Use a chair or wall for balance if needed.\r\n   - Muscles worked: quadriceps, hamstrings, glutes, and calves.', 'exercise/Assisted Lunges.mp4', '30 secs', '3 sets'),
('Backward Lunge ', 'Instructions: Step backward with one leg and lower your body until both knees are bent at a 90-degree angle. Return to the starting position and repeat on the other side.\r\n   - Muscles: Engages quadriceps, hamstrings, and glutes.', 'exercise/Backward Lunges.mp4', '5 mins', '10 reps'),
('Burpees ', 'Instructions: Start standing, then squat down and place your hands on the ground. Jump your feet back into a plank position, perform a push-up, jump your feet back to the squat position, and then jump explosively into the air with arms raised.\r\nMuscles: Full-body exercise targeting legs, chest, arms, and core muscles.\r\n', 'exercise/Burpees.mp4', '30 secs', '8 reps'),
('Butt Bridge ', 'Instructions: Lie on your back with knees bent and feet flat on the ground. Lift your hips up towards the ceiling, squeezing your glutes at the top.\r\nMuscles: Targets the glutes, hamstrings, and lower back.', 'exercise/Butt Bridge.mp4', '30 secs', '3 sets'),
('Butt Kicks', 'Instructions: Jog in place while bringing your heels up towards your glutes with each step.\r\n    - Muscles: Targets the hamstrings and calves.\r\n', 'exercise/Butt Kicks.mp4', '30 sets', '3 sets'),
('Cobra Stretch', 'Instructions: Lie on your stomach with palms on the ground under your shoulders. Push up, lifting your chest off the ground while keeping your hips down.\r\nMuscles: Stretches the chest, abdominals, and hip flexors.', 'exercise/Cobra Stretch.mp4', '30 secs', '30 set'),
('Crab Kicks', 'Instructions: Sit on the ground with knees bent and hands behind you. Lift your hips off the ground and kick one leg up towards the ceiling, then alternate legs.\r\n   - Muscles: Works the glutes, hamstrings, and core muscles.', 'exercise/Crab kick.mp4', '30 secs', '3 sets'),
('Cross Arm Crunches ', 'Instructions: Lie on your back with knees bent and feet flat on the ground. Crunch up and bring one elbow towards the opposite knee, then switch sides.\r\nMuscles: Targets the obliques, rectus abdominis, and hip flexors.', 'exercise/Cross arm crunches.mp4', '50 secs', '18 reps'),
('Cross Knee Plank', ' Instructions: Start in a plank position. Bring one knee towards the opposite elbow, then return to plank position and alternate sides.\r\nMuscles: Engages the core, obliques, and stabilizer muscles.', 'exercise/Cross Knee Plank.mp4', '45 secs', '28 reps'),
('Deep Squat Hold', '- Stand with your feet slightly wider than hip-width apart, toes pointed slightly outwards.\r\n   - Squat down as low as you can while keeping your heels on the ground.\r\n   - Keep your chest up and back straight, with your hands together in front of your chest for balance.\r\n   - Hold the squat position for 60 seconds.\r\n   - Muscles worked: quadriceps, hamstrings, glutes, and hip flexors.\r\n', 'exercise/Deep Squat Hold.mp4', '5 mins', '60 secs , 3 sets'),
('Diagonal Plank', ' Instructions: Start in a plank position with forearms on the ground. Rotate your torso to bring one elbow up towards the opposite shoulder, then return to plank position and repeat on the other side.\r\n   - Muscles: Engages the obliques, core muscles, shoulders, and stabilizer muscles.', 'exercise/Diagonal Plank.mp4', '5 mins', '10 reps'),
('Diagonal Plank (again)', ' Instructions: Start in a plank position with forearms on the ground. Rotate your torso to bring one elbow up towards the opposite shoulder, then return to plank position and repeat on the other side.\r\n   - Muscles: Engages the obliques, core muscles, shoulders, and stabilizer muscles.', 'exercise/Diagonal Plank.mp4', '5 mins', '10 reps'),
('Diamond Push-Ups', 'Instructions: Start in a push-up position with hands close together under your chest, forming a diamond shape with your thumbs and index fingers. Lower your body towards the ground by bending your elbows, then push back up.\r\nMuscles: Targets the triceps, chest, and shoulders.\r\n', 'exercise/Diamond push ups.mp4', '30 secs', '8 reps'),
('Doorway Curls (Left/Right) ', 'Instructions: Stand inside a doorway with one arm extended straight out to the side, palm facing the door frame. Grasp the door frame with your hand and lean forward slightly, then bend your elbow to pull yourself towards the door frame, keeping your upper arm stationary. Return to the starting position and repeat.\r\nMuscles: Targets the biceps.', 'exercise/Doorway Curls Left.mp4', '40 secs', '8 reps each'),
('Dynamic Chest ', ' Instructions: Stand with feet shoulder-width apart and arms extended straight out to the sides at shoulder height. Bring your arms together in front of your chest, crossing them, then return to the starting position.\r\nMuscles: Targets the chest muscles (pectorals).', 'exercise/Dynamic Chest.mp4', '30 secs', '10 reps'),
('Elbows Back ', ' Instructions: Stand with feet shoulder-width apart and arms bent at 90 degrees by your sides. Squeeze your shoulder blades together as you pull your elbows back, then return to the starting position.\r\nMuscles: Targets the upper back muscles (rhomboids and trapezius).', 'exercise/Elbow Back Stretch.mp4', '5 mins', '10 reps'),
('Flutter Kicks ', ' Instructions: Lie on your back with legs straight. Lift your legs off the ground a few inches and alternate kicking them up and down in a fluttering motion.\r\n   - Muscles: Engages the lower abs and hip flexors.\r\n', 'exercise/FLutter Kicks.mp4', '3 mins', '3 sets'),
('Glute Kick Back (left)', '- Instructions: Start on all fours. Extend one leg straight back and slightly across the body, focusing on squeezing the glutes.\r\n- Muscles: Works the glutes and hamstrings.', 'exercise/Glute Kick Back Left.mp4', '3 mins', '2X times'),
('Glute Kick Back (Right)', 'Instructions: Lie on your back with the soles of your feet together and knees bent out to the sides. Push your knees outward while squeezing your glutes.\r\nMuscles: Engages the inner thigh (adductors) and glutes.', 'exercise/Glute Kick Back Right.mp4', '20 mins', '3 sets, 2 times'),
('Heel Touch ', 'Instructions: Lie on your back with knees bent and feet flat on the ground. Crunch up and reach your hands towards your heels, alternating sides.\r\nMuscles: Engages the obliques and rectus abdominis.\r\n', 'exercise/Heal Touch.mp4', '20 secs', '20 reps'),
('Heels to the Heavens ', 'Instructions: Lie on your back with legs straight up towards the ceiling. Lift your hips off the ground towards the ceiling, then lower back down.\r\nMuscles: Engages the lower abs and hip flexors.', 'exercise/Heels to Heavens.mp4', '20 secs', '12 reps'),
('High stepping', 'Instructions: Lift your knees as high as possible while walking or jogging in place.\r\nMuscles: Engages hip flexors, quadriceps, and core muscles.', 'exercise/High Stepping.mp4', '3 Mins', '3 sets, 10 times'),
('In and Outs', 'nstructions: Sit on the floor with knees bent and feet lifted off the ground. Lean back slightly and then bring your knees towards your chest and extend them out again.\r\nMuscles: Works the lower abs and hip flexors.', 'uploads/exercisepcos.mp4', '30 secs', '8 reps'),
('Inchworms', 'Instructions: Stand with feet hip-width apart. Bend forward at the waist and place your hands on the ground. Walk your hands forward until you are in a plank position, then walk them back towards your feet and return to standing.\r\nMuscles: Engages the core, shoulders, hamstrings, and lower back.', 'exercise/Inch worms.mp4', '1 mins', '6 reps'),
('Incline Push-Ups', 'Instructions: Place hands on an elevated surface (e.g., bench or step) with feet on the ground. Lower your body until your chest nearly touches the surface, then push back up to the starting position.\r\n   - Muscles: Targets the chest, shoulders, and triceps.', 'exercise/Incline.mp4', '5 mins ', '10 reps'),
('Incline Push-Ups (again)', 'Instructions: Place hands on an elevated surface (e.g., bench or step) with feet on the ground. Lower your body until your chest nearly touches the surface, then push back up to the starting position.\r\n   - Muscles: Targets the chest, shoulders, and triceps.', 'exercise/Incline.mp4', '5 mins ', '10 reps'),
('Jumping Jacks', 'Instructions: Start with your feet together and arms at your sides. Jump while spreading your legs out to the sides and raising your arms above your head. Return to the starting position and repeat.\r\nMuscles: Works the cardiovascular system, engages leg muscles, including quadriceps, hamstrings, calves, and also works shoulder muscles.\r\n', 'exercise/Jumping Jacks.mp4', '20 mins', '3 sets, 2 times'),
('Left Side Lying Forward Leg Lift', 'Instructions: Lie on your left side with legs straight. Lift your top leg up towards the ceiling, keeping it straight.\r\nMuscles: Targets the outer thigh (abductors) and glutes.', 'exercise/Left leg lateral raise copy.mp4', '15 mins', '2 sets, 3 times'),
('Leg Barbell Curl (Left)', ' - Instructions: Hold a barbell behind your legs with your feet shoulder-width apart. Bend your knee to lift the barbell towards your glutes, then lower it back down.\r\n- Muscles: Targets the hamstrings and glutes.\r\n', 'exercise/Leg barbell curl left.mp4', '5 mins', '8 reps each'),
('Leg Barbell Curl (right)', ' - Instructions: Hold a barbell behind your legs with your feet shoulder-width apart. Bend your knee to lift the barbell towards your glutes, then lower it back down.\r\n- Muscles: Targets the hamstrings and glutes.\r\n', 'exercise/Leg barbell curl Right.mp4', '5 mins', '8 reps each'),
('Leg Raises', 'Instructions: Lie on your back with legs straight. Lift both legs up towards the ceiling, then slowly lower them back down without letting them touch the ground.\r\n   - Muscles: Targets the lower abs and hip flexors.', 'exercise/Leg Raise.mp4', '5 mins', '8 reps'),
('Lying Twist Stretch (Left/Right)', 'Instructions: Lie on your back with arms extended out to the sides in a T position. Bend your knees and lift them towards your chest. Keeping your shoulders on the ground, lower your knees towards the left side until you feel a stretch in your lower back and obliques. Hold for 30 seconds. Then, repeat the stretch on the right side.\r\nMuscles: Stretches the lower back, obliques, and thoracic spine.', 'exercise/Lying Twist Stretch Left.mp4', '30 secs each', '30 sets'),
('Mountain Climber', 'Instructions: Get into a plank position, then alternate bringing your knees towards your chest in a running motion.\r\nMuscles: Engages the core, shoulders, and hip flexors.\r\n', 'exercise/Mountain Climber.mp4', '30 secs', '3 reps'),
('Mountain Climber (again)', 'Instructions: Get into a plank position, then alternate bringing your knees towards your chest in a running motion.\r\nMuscles: Engages the core, shoulders, and hip flexors.\r\n', 'exercise/Mountain Climber.mp4', '30 secs', '3 reps'),
('Pendulum Swings', 'Instructions: Stand with feet shoulder-width apart. Swing one leg out to the side and then back to the center, then swing it out to the other side. Repeat.\r\nMuscles: Works the hip abductors, adductors, and glutes.', 'exercise/Pendulam Swings.mp4', '30 secs', '3 sets'),
('Plank', 'Instructions: Hold a push-up position with elbows bent and body in a straight line from head to heels.\r\nMuscles: Engages the core, shoulders, chest, and back muscles.\r\n', 'exercise/Plank.mp4', '30 secs', '3 reps'),
('Plank Taps', '- Instructions: Start in a plank position on your hands. Tap one hand to the opposite shoulder, then alternate sides.\r\n- Muscles: Engages the core, shoulders, and stabilizer muscles.\r\n', 'exercise/Plank Taps.mp4', '5 mins', '10 reps'),
('Plie Squats', ' Instructions: Stand with feet wider than shoulder-width apart, toes turned out. Lower your body by bending your knees, keeping your back straight. Return to the starting position.\r\n   - Muscles: Targets the quadriceps, hamstrings, inner thighs (adductors), and glutes.', 'exercise/Pile Squats.mp4', '5 mins', '8 reps'),
('Push-Ups ', ' Instructions: Start in a plank position with hands slightly wider than shoulder-width apart. Lower your body until your chest nearly touches the ground, then push back up to the starting position.\r\n   - Muscles: Engages the chest, shoulders, and triceps.\r\n', 'exercise/Pushes.mp4', '5 mins', '10 reps'),
('Push-Ups (again)', ' Instructions: Start in a plank position with hands slightly wider than shoulder-width apart. Lower your body until your chest nearly touches the ground, then push back up to the starting position.\r\n   - Muscles: Engages the chest, shoulders, and triceps.\r\n', 'exercise/Pushes.mp4', '5 mins', '10 reps'),
('Quad Stretch with Wall (Left/Right)', ' Instructions: Stand facing a wall. Bend one knee and lift your foot towards your glutes. Hold onto your foot with the corresponding hand and gently press your hips forward.\r\nMuscles: Stretches the quadriceps.\r\n', 'uploads/exercisepcos.mp4', '30 sec', '8 reps'),
('Quick Feet ', 'Instructions: Rapidly tap your feet on the ground, keeping them close to the floor and moving as quickly as possible.\r\nMuscles: Works on foot speed and agility, engaging calves, quadriceps, and hamstrings.', 'exercise/Quick Feet.mp4', '30 secs', '3 sets'),
('Reclined Oblique Twist', 'Instructions: Sit on the ground with knees bent and feet lifted off the floor. Lean back slightly and rotate your torso from side to side, touching the ground beside your hips with each twist.\r\nMuscles: Targets the obliques, rectus abdominis, and stabilizer muscles.', 'exercise/Reclined Oblique Twist.mp4', '20 secs', '16 reps'),
('Reverse Crunches', 'Instructions: Lie on your back with knees bent and feet lifted off the ground. Contract your abs to lift your hips off the floor, bringing your knees towards your chest, then lower back down.\r\nMuscles: Targets the lower abs and hip flexors.\r\n', 'exercise/Reverse Crunches.mp4', '45 secs', '18 reps'),
('Right Side Lying Forward Leg Lift', 'Instructions: Same as left side lying forward leg lift, but on your right side.\r\nMuscles: Targets the outer thigh (abductors) and glutes.', 'exercise/Right leg lateral raise.mp4', '15 mins', '2 sets, 2 times'),
('Russian Twist ', 'Instructions: Sit on the ground with knees bent and feet lifted off the floor. Lean back slightly and rotate your torso from side to side, touching the ground beside your hips with each twist.\r\n   - Muscles: Engages the obliques, rectus abdominis, and stabilizer muscles.\r\n', 'exercise/Russian Twist.mp4', '30 secs', '16 reps'),
('Scissors', 'Instructions: Lie on your back with legs straight. Lift one leg up towards the ceiling while keeping the other leg hovering slightly above the ground. Switch legs in a scissor-like motion.\r\nMuscles: Works the lower abs, hip flexors, and quadriceps.', 'uploads/exercisepcos.mp4', '20 mins', '2 sets, 3 times'),
('Shoulder Stretch (Left)', ' - Stand or sit tall with your shoulders relaxed.\r\n   - Reach one arm across your body, using the opposite hand to gently press the arm towards your chest.\r\n   - Hold the stretch for 30 seconds, then switch sides.\r\n   - Muscles worked: stretches the deltoids and upper back muscles.\r\n', 'exercise/Shoulder stretch Left.mp4', '30 sec', '3 sets'),
('Shoulder Stretch (right)', '- Stand or sit tall with your shoulders relaxed.\r\n   - Reach one arm across your body, using the opposite hand to gently press the arm towards your chest.\r\n   - Hold the stretch for 30 seconds, then switch sides.\r\n   - Muscles worked: stretches the deltoids and upper back muscles.', 'exercise/Shoulder stretch Right.mp4', '30 secs', '3 sets'),
('Side Crunches (Left)', ' Instructions: Start in the same position as the side lying leg lift. Instead of lifting straight up, kick your top leg slightly back.\r\nMuscles: Targets the glutes and hamstrings.', 'exercise/Side Crunch Left.mp4', '20 mins', '2 sets '),
('Side Crunches (Right)', 'Instructions: Lie on your back with knees bent. Lift your shoulder blades off the ground and twist to one side, bringing elbow towards the opposite knee.\r\n    - Muscles: Targets the obliques and rectus abdominis.', 'exercise/Side Crunch Right.mp4', '5 mins', '16 reps each'),
('Side Hop ', ' Instructions: Jump laterally from side to side, landing softly with bent knees.\r\n    - Muscles: Engages the calves, quadriceps, hamstrings, and lateral stabilizer muscles.', NULL, '30 secs', '3 sets'),
('Side Lunges', 'Instructions: Start standing with feet together. Take a wide step to the side, bending one knee while keeping the other leg straight. Return to the starting position and repeat on the other side.\r\nMuscles: Targets the quadriceps, hamstrings, inner thighs, and glutes.', 'exercise/Side Lunges.mp4', '30 secs', '3 reps'),
('Side Lying Leg Lift Left/Right', 'Instructions: Lie on your side with legs straight. Lift your top leg up towards the ceiling, keeping it straight.\r\nMuscles: Works the outer thigh (abductors) and glutes.\r\n', 'exercise/Side Crunch Left.mp4', '15 mins', '2 sets, 2 times'),
('Skipping Without Rope ', 'Instructions: Mimic the motion of skipping without an actual rope, focusing on the movement of arms and legs.\r\nMuscles: Engages calves, quadriceps, hamstrings, and core muscles.\r\n', 'exercise/Skipping Without Rope.mp4', '30 secs', '3 sets'),
('Squat Pulses', 'Instructions: Perform small, quick pulses in the squat position, staying low with knees bent.\r\nMuscles: Engages quadriceps, hamstrings, glutes, and calves.', 'exercise/Squat pulses.mp4', '30 secs', '3 sets'),
('Standing Biceps Stretch (Left/Right) ', 'Instructions: Stand with feet shoulder-width apart and extend one arm straight out in front of you with your palm facing up. Use your other hand to gently pull back on your fingers, stretching your forearm and biceps. Hold the stretch for 30 seconds, then repeat on the other side.\r\nMuscles: Stretches the biceps and forearm muscles.', 'exercise/Standing Biceps Stretch.mp4', '30 secs', '30 reps'),
('Standing Bicycle Crunches ', 'Instructions: Stand with hands behind your head, elbows wide. Bring your right elbow towards your left knee while lifting it, then switch to bring your left elbow towards your right knee.\r\n   - Muscles: Targets the obliques, rectus abdominis (front abs), and hip flexors.', 'exercise/Standing Bicycle Crunches.mp4', '30 secs', '14 reps'),
('Standing Bicycle Crunches (again)', '- Instructions: Stand with hands behind your head, elbows wide. Bring your right elbow towards your left knee while lifting it, then switch to bring your left elbow towards your right knee.\r\n- Muscles: Targets the obliques, rectus abdominis (front abs), and hip flexors.', 'exercise/Standing Bicycle Crunches.mp4', '5 mins', '14 reps'),
('Star Crawl', 'Instructions: Start in a plank position. Lift one hand and the opposite foot off the ground, reaching them out to the side, then return to plank position and repeat on the other side.\r\n   - Muscles: Engages the core, shoulders, and stabilizer muscles.', 'exercise/Star crawl.mp4', '5 mins', '10 reps'),
('Straight Leg Fire Hydrant (Left)', '- Instructions: Start on all fours with a straight leg. Lift one leg out to the side, keeping it straight and parallel to the ground.\r\n- Muscles: Targets the outer thighs (abductors) and glutes.', 'exercise/Straight leg fire hydrant left.mp4', '3 mins', '3X times'),
('Straight Leg Fire Hydrant (Right)', '- Instructions: Start on all fours with a straight leg. Lift one leg out to the side, keeping it straight and parallel to the ground.\r\n- Muscles: Targets the outer thighs (abductors) and glutes.\r\n', 'exercise/Straight Leg Fire Hydrant Right.mp4', '3 mins', '10 rep each'),
('Triceps Dips', 'Instructions: Sit on the edge of a chair or bench with hands placed next to hips. Slide your butt off the edge and bend your elbows to lower your body towards the ground, then push back up to the starting position.\r\n   - Muscles: Targets the triceps.', 'exercise/Triceps Dips.mp4', '5 mins', '10 reps'),
('Triceps Dips (again)', 'Instructions: Sit on the edge of a chair or bench with hands placed next to hips. Slide your butt off the edge and bend your elbows to lower your body towards the ground, then push back up to the starting position.\r\n   - Muscles: Targets the triceps.', 'exercise/Triceps Dips.mp4', '5 mins', '10 reps'),
('Triceps Stretch (Left/Right) ', 'Instructions: Stand or sit tall and reach your left arm overhead, bending at the elbow, and placing your hand down your back. Use your right hand to gently push your left elbow further down your back until you feel a stretch in your triceps. Hold the stretch for 30 seconds, then repeat on the other side.\r\nMuscles: Stretches the triceps.', 'exercise/Triceps stretch Right.mp4', '30 secs', '30 secs each'),
('V-Hold ', ' Instructions: Sit on the ground and lean back slightly, lifting your legs off the ground and extending them out straight. Reach your arms forward parallel to the ground.\r\n    - Muscles: Engages the core, including the rectus abdominis and obliques.', 'exercise/V-Hold.mp4', '30 secs', '3 sets'),
('Walking Squats ', 'Instructions: Perform squats while moving forward. Keep your chest up, back straight, and weight in your heels.\r\nMuscles: Engages quadriceps, hamstrings, glutes, and core muscles.\r\n', 'exercise/Walking Squats.mp4', '30 secs', '3 reps'),
('X-Man Crunch ', ' Instructions: Lie on your back with arms and legs extended out to form an \'X\' shape. Crunch up, bringing opposite elbow towards opposite knee, then return to starting position.\r\n    - Muscles: Engages the obliques, rectus abdominis, and hip flexors.', 'exercise/X Man Crunch.mp4', '5 mins', '16 reps');

-- --------------------------------------------------------

--
-- Table structure for table `weight_graph`
--

CREATE TABLE `weight_graph` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `weight_value` decimal(10,2) NOT NULL,
  `recorded_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `months` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `weight_graph`
--

INSERT INTO `weight_graph` (`id`, `name`, `weight_value`, `recorded_at`, `months`) VALUES
(3, 'shobana', 44.00, '2024-04-12 10:20:39', 1),
(5, 'shradha', 65.00, '2024-04-16 06:17:26', 1),
(6, 'shradha', 65.00, '2024-04-16 06:19:35', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Admin`
--
ALTER TABLE `Admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `calender`
--
ALTER TABLE `calender`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `daily_steps`
--
ALTER TABLE `daily_steps`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `doctor_login`
--
ALTER TABLE `doctor_login`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `food_calorie`
--
ALTER TABLE `food_calorie`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `forgotpassword`
--
ALTER TABLE `forgotpassword`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `leaderboard`
--
ALTER TABLE `leaderboard`
  ADD UNIQUE KEY `Name` (`Name`);

--
-- Indexes for table `mcq_questions`
--
ALTER TABLE `mcq_questions`
  ADD PRIMARY KEY (`question_id`);

--
-- Indexes for table `medical_record`
--
ALTER TABLE `medical_record`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `meditation`
--
ALTER TABLE `meditation`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `personal_details`
--
ALTER TABLE `personal_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `score`
--
ALTER TABLE `score`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `signup`
--
ALTER TABLE `signup`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `today_progress`
--
ALTER TABLE `today_progress`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `videos`
--
ALTER TABLE `videos`
  ADD PRIMARY KEY (`exercise_name`);

--
-- Indexes for table `weight_graph`
--
ALTER TABLE `weight_graph`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Admin`
--
ALTER TABLE `Admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `calender`
--
ALTER TABLE `calender`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=91;

--
-- AUTO_INCREMENT for table `daily_steps`
--
ALTER TABLE `daily_steps`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `doctor_login`
--
ALTER TABLE `doctor_login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `food`
--
ALTER TABLE `food`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=113;

--
-- AUTO_INCREMENT for table `food_calorie`
--
ALTER TABLE `food_calorie`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `forgotpassword`
--
ALTER TABLE `forgotpassword`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `medical_record`
--
ALTER TABLE `medical_record`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `meditation`
--
ALTER TABLE `meditation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `personal_details`
--
ALTER TABLE `personal_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT for table `score`
--
ALTER TABLE `score`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `signup`
--
ALTER TABLE `signup`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;

--
-- AUTO_INCREMENT for table `today_progress`
--
ALTER TABLE `today_progress`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=91;

--
-- AUTO_INCREMENT for table `weight_graph`
--
ALTER TABLE `weight_graph`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
