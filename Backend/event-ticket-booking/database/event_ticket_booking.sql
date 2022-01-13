-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 13, 2022 at 07:36 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `event_ticket_booking`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `a_id` int(11) NOT NULL,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`a_id`, `firstname`, `lastname`, `email`, `password`) VALUES
(1, 'Bhaskar', 'Gogoi', 'thebhaskargogoi@gmail.com', '$2y$10$O2boTTznB6pV8aQy226nVOrA2HmpIkvxbjUn04QVtxt1ZPQ4tLhaG'),
(2, 'Admin', 'Super', 'admin@admin.com', '$2y$10$LxaqShmNVKaGVlKyCf39VOr7Ek1KaFzaCqzojxdXiMOKlByHzUM/C');

-- --------------------------------------------------------

--
-- Table structure for table `api_access_token`
--

CREATE TABLE `api_access_token` (
  `id` int(5) NOT NULL,
  `user_id` int(5) NOT NULL,
  `token` varchar(100) NOT NULL,
  `expiry_date` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `api_access_token`
--

INSERT INTO `api_access_token` (`id`, `user_id`, `token`, `expiry_date`) VALUES
(70, 21, 'otwwf72tj5GkSLzNkP1HTi3zfxaj2cY0WMmQqA6QRWQqCJrAhu', ''),
(71, 22, 'T2AISMSRib5LYn1#WIloaBKk2QZORpBEwc0ewaOVFhpl9ED2Oo', ''),
(72, 23, '1CiUxaBc6LX4pj4HK9FeRFshjS4AMmMmi76xi2xEMQFN*x5fSP', ''),
(73, 3, 'SRoRvVgda$7rgfT9kknwCrczmY2OCdRIBPPzhd$9YvBXqcAiMA', ''),
(74, 4, 'Hh1JwEufFDiUq9Mima72dre88azdZm*Tp2SyPIahD3PUq9u16X', ''),
(75, 3, 'uKc7w**I4xePOzcxhjdJeaYU7AfZj$3C7kqPToSlq7mlPjW985', ''),
(76, 3, 'o1W3scFOO*56znCyJuVY1CNaOIxW0WMdArVcb186OKwXm*rOm5', ''),
(77, 3, 'P$sIj3O*S3V6jqbeeh9hJFdHj$LmurKlA7wQZXDs62q1bOFnTF', ''),
(78, 3, 'kre8ME0L#PRD$rDFisF*j5xzKPjuLYAX2BmUmlKcUtgGB*9Vc9', ''),
(79, 3, 'wrsg*NyoKPPWBbsMVBIEZofPFs9NMP$NsmO$xzJQ2r3NiYfU2w', ''),
(80, 3, 'enK0E60eHWIDD7zbqbUlZeT9JXJivlTQpyFgskS9f7AVCuww69', ''),
(81, 23, 'XEZs8AESFAT3$wnkIPAjsH82SSKHU88nSFqdaFdN5*Ksvlh*vG', ''),
(82, 3, 'elTRmQ6FMV98ZoGsG1$K60l*f3Q8JoF97DCHQyjWx5L0ypp7tN', ''),
(83, 3, 'S6daIx0e0FpzCTR1pCYQbxogUxajxmh0$IEBcPaVuLbHsmjnuT', ''),
(84, 3, 'bwz2oXQiyx1yyFHqBlAwnTKa$A1J817LbtKzcQ9P9UAsMvfAIe', ''),
(85, 3, 'M9lCkLVfnLbqmJiIsG2Cfk6PxxbUdzi7tiN3jH2mYNZlIK2yfl', ''),
(86, 3, '4toa#AGhKaEEaqcX847aS4kKwsVqICKzeHyW0ZBb#IzAwFKRya', '11-07-2022'),
(87, 3, 'lswQOdnlukZ*l1FSSQUvKsPTvLCBb#3dVYF5a6gm$hVPw74XkM', '11-07-2022'),
(88, 24, 'wumyO64h6SCKG5MROPDzzAcX1o#7oKraxj#PlCVEpzsQNzh65$', ''),
(89, 25, 'Upnz5LR$n#0ILyXGZGYRKETHFAe2gFS#ifll0bEK3K6DSdfErr', ''),
(90, 3, '6gZFetlIsA8LVc1Yy#I8#dvCEDhz7c6hk22EexsJmvchOJbuYV', '12-07-2022'),
(91, 3, 'SLjC$TeioBl2dfaysfGEncKq7PSL3YxGJ29#F4j6hLlOkiWxrH', '12-07-2022');

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `booking_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `no_of_tickets` int(11) NOT NULL,
  `total_price` varchar(11) NOT NULL,
  `payment_reference_id` varchar(50) NOT NULL,
  `booking_date` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`booking_id`, `event_id`, `user_id`, `no_of_tickets`, `total_price`, `payment_reference_id`, `booking_date`) VALUES
(43, 69, 3, 1, '299', '546529731', ''),
(44, 69, 3, 1, '299', '108728557', ''),
(45, 63, 3, 1, '600', '518328363', ''),
(46, 69, 3, 1, '299', '0', '12/01/2022'),
(47, 69, 3, 1, '299', '0', '13/01/2022'),
(48, 69, 3, 1, '299', '0', '13/01/2022'),
(49, 69, 3, 1, '299', 'pay_IjJ39xigbUKbhc', '13/01/2022');

-- --------------------------------------------------------

--
-- Table structure for table `contact`
--

CREATE TABLE `contact` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `message` text NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `event_id` int(11) NOT NULL,
  `event_name` varchar(50) NOT NULL,
  `event_type` varchar(20) NOT NULL,
  `event_mode` varchar(10) NOT NULL,
  `hosted_by` varchar(30) NOT NULL,
  `date` varchar(30) NOT NULL,
  `time` varchar(30) NOT NULL,
  `duration` varchar(3) NOT NULL,
  `price` double NOT NULL,
  `max_tickets` int(5) NOT NULL,
  `about` text NOT NULL,
  `address_area` varchar(50) DEFAULT NULL,
  `address_locality` varchar(50) DEFAULT NULL,
  `address_city` varchar(50) DEFAULT NULL,
  `address_state` varchar(50) DEFAULT NULL,
  `address_pin` int(8) DEFAULT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`event_id`, `event_name`, `event_type`, `event_mode`, `hosted_by`, `date`, `time`, `duration`, `price`, `max_tickets`, `about`, `address_area`, `address_locality`, `address_city`, `address_state`, `address_pin`, `status`) VALUES
(41, 'Kenny Sebastian : Explain Yourself', 'Stand Up', 'Offline', 'Kenny Sebastian', '2021-08-27', '17:45:00', '1', 200, 700, 'A one hour show where Kenny takes you through a process of explaining a lot of things. Including himself. With anecdotes, definitions and jokes, A humorous intimate live experience.', 'The Habitat', 'Khar West', 'Mumbai', 'Maharashtra', 400052, 'Approved'),
(42, 'Good Vibes-Music', 'Music', 'Online', 'Zoom', '2021-09-30', '08:00:00', '3', 450, 500, 'Experience the power of elegant voices as they come together to light up your day with pop, indie pop, rap and various other genres!', '', '', '', '', 0, 'Approved'),
(43, 'Music Unfiltered Open Mic and Jam Session', 'Music', 'Offline', 'Lewis', '2021-04-17', '16:00:00', '2', 300, 1000, 'Hooted1ce is going to its new venue for the singer-songwriters and musicians and singers who do covers,and for the people who love music.So, if you are a singer-songwriter or want to experience the music of the people which comes from their heart and which has never been heard and the music which is there already and people sing as there own, the music which is unfiltered and raw, the music which has emotions, dedication and efforts, then this is the right place. Come and witness the unheard.Come and Join for some jam session along with our open mic.To register whatsapp on 7045011032 with your name.', 'Funkaar Dance Studio', 'Andheri', 'Mumbai', 'Maharashtra', 400053, 'Approved'),
(44, 'Music Unfiltered Open Mic ft. Viraj', 'Music', 'Offline', 'Viraj', '2021-06-28', '15:00:00', '2', 399, 600, 'Hooted1ce is coming to Funkaar for the singer- songwriters and musicians and singers who do covers, and for the people who love music. So, if you are a singer- songwriter or want to experience the music of the people which comes from their heart and which has never been heard and the music which is there already and people sing as there own, the music which is unfiltered and raw, the music which has emotions, dedication and efforts, then this is the right place. Come and witness the unheard. Come and Join for some jam session along with our open mic. To register whatsapp on 7045011032 with your name', 'Funkaar Dance Studio', 'Andheri', 'Mumbai', 'Maharashtra', 400053, 'Approved'),
(45, 'DiGi Open Mic-Music', 'Music', 'Online', 'DiGi', '2021-06-18', '13:00:00', '2', 299, 300, 'To be bedazzled to the elegant voice is what attracts the masses. When words have the power to come together and brighten our day we feel blessed. Therefore The Mental Talkies presents to you DiGi Open Mic Music. Come join us and freshen up your mind!', '', '', '', '', 0, 'Approved'),
(47, 'Himalayan River Painting Workshop', 'Workshop', 'Offline', 'Bombay Drawing Room', '2021-07-07', '10:00:00', '5+', 1100, 200, 'Bombay Drawing Room brings you OFFLINE workshops!! Yay!\r\n\r\nFinally the wait is over! Explore your creativity and express yourself through art in our offline workshops. The workshops will be hosted in the café and we will be providing you all the material which is required for the event. Our experienced artist will be guiding you throughout the workshop with step by step instructions! No need to have any experience in painting or sketching. Limited seats are available.', 'Episode One', 'Powai', 'Mumbai', 'Maharashtra', 400076, 'Approved'),
(49, 'Art & Drawing - Junior Kids', 'Workshop', 'Offline', 'Hobbystation', '2021-09-22', '10:00:00', '2', 599, 100, 'Give your child a chance to embrace their artistic side.\r\n\r\n\r\n\r\nIn this course, we teach the child the fundamentals of drawing and colouring like drawing objects of different shapes perfectly, simple memory drawing and landscapes, colouring (using crayons, colour pencils and brush pens)including shading and knowledge of primary and secondary colours. With each class, you will see your child getting more and more confident with his/her drawing and colouring skills.', 'Paltan Bazar', 'Paltab Bazar', 'Guwahati', 'Assam', 780001, 'Approved'),
(50, 'Stop Motion Animation 2-Day Workshop', 'Workshop', 'Offline', 'Toiing', '2021-07-04', '10:00:00', '5+', 587, 200, 'Introduce your kids to the awesome world of Stop Motion Animation where they learn to make their very first video using the stop motion animation technique.\r\n\r\nLed by a live instructor, this workshop features a masterclass by the legendary Cyrus Broacha and stop motion expert Rucha Dhayarkar.\r\n\r\nDay 1: Kids learn the Stop Motion Animation technique and make their first stop motion video using household things\r\n\r\nDay 2: Kids learn the art of storytelling and create a storyboard for their project\r\n\r\nPost Class Assignment: Make your first Stop Motion Animation Short Film\r\n\r\nWorkshop Details:\r\n\r\nAge: 7 to 14 years\r\n\r\nNo. of Session: 2\r\n\r\nSession Duration: 60 Minutes Session/Day\r\n\r\nFormat: Live Online\r\n\r\nDate & Time: Select batch while adding to cart\r\n\r\nSkills Developed:\r\n\r\nStorytelling and Creative Expression\r\nCollaboration & Team Work\r\nPlanning & Execution\r\nPatience and Attention To Details', 'Chiring Chapori', 'Chowkidingee', 'Dibrugarh', 'Assam', 786003, 'Approved'),
(52, 'Karwan Theatre Group Mumbai DHAPPA', 'Play', 'Offline', 'Karwan Theatre Group', '2021-08-30', '18:00:00', '2', 499, 250, 'Writer & Director: Akshay Mishra\r\n\r\nCast: Puneet Issar, Sharon Chandra, Pavitra Sarkar & Anuradha Athlekar\r\n\r\nShyam, a film director, is intrigued by Iravati, a Kathak dancer, and intends to cast her in his next film. But her brother Kumar, a failed actor, is adamant about keeping Shyam away from Iravati. Instead of it being an act of jealousy, Kumar harbours a deep dark secret. Set against the 1950s era, \'Dhappa\' is submerged in mystique and drama.', 'Prithvi Theatre', 'Juhu', 'Mumbai', 'Maharashtra', 400049, 'Approved'),
(53, 'Marche`s Pop Up Bazar - Summer Flea', 'Exhibition', 'Offline', 'Marches', '2021-06-01', '10:00:00', '5+', 500, 1200, 'We bring you the hottest Flea market in town, with a little something for all you beautiful people out there. Come be a part of this magical event with us. With a specially curated selection of displays, brands, labels, and artists, we promise you a fun-packed weekend with us. Also, we have a curated line-up of live music, an over-flowing bar, and a food truck. All of this in the middle of a golf course! We can\'t wait to have you over!', 'Boulder Hills', 'Khajaguda', 'Hyderabad', 'Telengana', 500032, 'Cancelled'),
(54, 'The Dineout SteppinOut Night Market', 'Exhibition', 'Offline', 'Dineout', '2021-07-15', '09:30:00', '5+', 799, 1500, 'The SteppinOut Night Market brings together the best of shopping, lifestyle, food and fun! This year SteppinOut by Dineout is celebrating 5 years of Night Market, and their anniversary edition is going to be bigger, better and full of exciting elements. \r\n\r\n\r\n\r\nWith over 70+ shopping stalls, 20+ food stalls, paint bar, kids zone, pet zone, spa, live music, games, comedy shows, pottery workshops - there is something for everyone. It is a flea and shopping experience redefined-- all in a new socially distanced avatar at the magnificent Jayamahal Palace in the heart of Bengaluru. \r\n\r\n\r\n\r\nFind the country`s most loved brands, hand-picked & curated specially for you. Shop from a wide collection of clothes, footwear, accessories & home decor among many other things! Indulge yourself in the variety that Bangalore`s best restaurants have to offer along with your furry friends. ', 'Jayamahal Palace Hotel', 'Nandi Durga Road', 'Bengaluru', 'Telengana', 560046, 'Approved'),
(56, 'Soup', 'Stand Up', 'Offline', 'Soup', '2021-08-12', '19:00:00', '2', 450, 500, 'It\'s just Tuesday, and after that long day of meetings, calls, and pretending to work, time to unwind with Tacos. This is a comedy show with a crunch outside (the host), but also a delicious filling inside (2 handpicked comedians who will be veg or non-veg depending on their choice). Real tacos have cheese, but this show will make you smile like you were saying cheese. \r\n\r\nDisclaimer\r\n\r\n1. Line up is subject to change depending on artist availability\r\n2. Each artist represents their own opinions. Please be prepared that they may not align with your views.\r\n3. Recording of any part of the show would be a violation of intellectual property agreements of the artist', 'Kolkata', 'Mumbai', 'Mumbai', 'Assam', 786303, 'Approved'),
(57, 'Monali Thakur Teaches Singing', 'Music', 'Online', 'Monali Thakur ', '2021-07-22', '10:00:00', '1', 299, 300, 'Learning from your favourite singer is no longer a dream! Learn music from award-winning playback singer Monali Thakur. In this course, Monali shares her experiences and techniques to become a singer. She focuses on crucial topics like breathing, pitching and taking care of your voice, etc.', '', '', '', '', 0, 'Approved'),
(58, 'Teen Prahar - Online', 'Music', 'Online', 'Ruhaniyat', '2021-07-30', '10:00:00', '3', 200, 400, 'Teen Prahar an extended evening concert completes a decade of its existence this year. This festival that aims to give a treat of ragas that are not heard in the regular night concerts also feature artists that are from three different stages in creative life (young talent, maestro-in-the-making & great masters).', '', '', '', '', 0, 'Approved'),
(59, 'Good Vibes-Music', 'Music', 'Offline', 'A.R Rahman', '2021-06-20', '19:16:00', '4', 600, 700, 'Experience the power of elegant voices as they come together to light up your day with pop, indie pop, rap and various other genres!\r\n\r\n\r\n\r\nThe Mental Talkies presents to you a Musical event that will freshen up your mind!\r\n\r\n\r\n\r\nHurry up and book your tickets now to grab yourself a free voucher!', 'mumbai', 'Mumbai', 'Mumbai', 'Mumbai', 746478, 'Approved'),
(60, 'NCPA - CITI (Aadi Anant)', 'Music', 'Online', 'Aadi Anant', '2021-09-08', '12:00:00', '3', 500, 300, 'Featuring Hariprasad Chaurasia (Bansuri) with Bhawani Shankar (Pakhavaj), Vijay Ghate (Tabla) & Jayanti Gosher (Guitar)\r\n\r\nIndia is one of the most musical & musically diverse countries in the world. Apart from some pan Indian forms, each region has its own distinctly rich legacy of music traditions\r\n\r\nHaving originated in the Vedic period (1500-900 BC), historically, the evolution of Indian music has been through various stages. From ritualistic hymns to folk melodies & ragas, the journey has continued relentlessly with classical & semi classical genres, leading up to crossover endeavours.\r\n\r\nAmong the myriad musical instruments practiced in India, bamboo flute or the bansuri is perhaps the most primitive & ancient instrument, It has a ubiquitous presence in all types of music including its well-known mythological connection with Lord Krishna, the cowherd of Brindavan.\r\n\r\nHariprasad Chaurasia, the legendary King of bansuri, will take the listeners on a musical discovery encompassing the broad canvas of Indian music. Starting with folk tunes, moving on to semi-classical & classical genres, the exploration will conclude with a fusion presentation.\r\n\r\n\r\n\r\nThis event was staged at the Tata Theatre on 8th December 2013\r\n\r\nA free webcast on the NCPA Facebook page & YouTube channel.\r\n\r\nPlease note: The videos will be available for a week post the streaming.', '', '', '', '', 0, 'Approved'),
(61, 'Creating A Dark Comedy', 'Talks', 'Online', 'Planet Paradox', '2021-08-05', '23:00:00', '4', 300, 700, 'We\'re excited to introduce FC Front Row - a collective of film and entertainment enthusiasts by Film Companion. We love watching. We cherish the creators. We revel in conversations about craft. We celebrate stories and artists.\r\n\r\nJoin us to watch, discuss, dissect and to have a good time!\r\n\r\nLook forward to\r\n\r\nOnline screenings, premieres, masterclasses, meet & greet with critics, discussions with artists, industry experts and upcoming content creators, The FC Front Row gives you first access to a range of exciting activities and programs.', '', '', '', '', 0, 'Approved'),
(62, 'Puppet Show', 'Exhibition', 'Offline', 'Puppet Show', '2021-08-02', '09:00:00', '3', 499, 100, 'Escape into a world of imagination with our puppeteer and have a great time in the lockdown period.\r\n\r\nEnjoy this puppet show and have a great time with your friends/family.', 'Kolkata', 'Kolkata', 'Kolkata', 'West Bengal', 786303, 'Approved'),
(63, 'Music Unfiltered Open Mic For Covers and Originals', 'Music', 'Online', 'Artist Krupa Shah', '2021-08-27', '08:00:00', '2', 600, 500, 'Hooted1ce is coming on Zoom with its open mic for the singer-songwriters, musicians and singers who do covers, and for the people who love music. we always believe in giving the platform to upcoming artists and help them go to the horizons. You will experience the extraordinary from the ordinary musicians and artists. So, if you are a singer-songwriter or want to experience the music of the people which comes from their heart and which has never been heard and the music which is there already and people sing as there own, the music which is unfiltered and raw, the music which has emotions, dedication and efforts, then this is the right place. Come and witness the unheard or register to participate.\r\n\r\nTo register Whatsapp on +91 -7045011032 with your name and also you need to book your self a ticket.\r\n\r\nThe event will be hosted by Rupali Dubey.', '', '', '', '', 0, 'Approved'),
(64, 'Smartphone Photography - Unleash the Pro Mode', 'Exhibition', 'Online', 'Ankita Shrivastav', '2021-08-28', '20:00:00', '1', 299, 400, 'Smartphone Photography - Unleash the Pro Mode, Lens, & External Applications:\r\n\r\nThis part of the course is to learn how to operate the native camera application in the smartphones. As smartphone native camera doesn\'t provide you the flexibility on video making & we must need to switch to external camera applications, which are available in \"Google Play Store\" for Android operating system & Appstore for iPhones. \r\n\r\n\r\n\r\nThis course is best suitable for Beginners to Professional who wants to start YouTube channel and share the best videos & achieve good traffic on based on photography skills. ', '', '', '', '', 0, 'Approved'),
(65, 'Art and Craft Online Workshop', 'Workshop', 'Online', 'Planet Paradox', '2021-07-01', '09:11:00', '3', 399, 400, 'Banjara Gypsy brings amazing artist - Chandrima Mandal, She holds an expertise in training kids and adults.  It\'s alright if you are totally a beginner or an expert. we have designed upcoming modules depends on your comfort. \r\n\r\nLet\'s start with Beginners level Class: \r\n\r\nDescription of the art work: Learn the layering technique in poster colours. It is a 3-Layer painting which has a combination of brush painting and blending method. \r\n\r\nStationaries required: \r\n\r\n1. Poster colours \r\n\r\n2. Paint Brush - Flat brush - min Size 11.  Medium brush - size 6 to 9  Thin brush : Size 1 and 0 \r\n\r\n3. Spare Unused Toothbrush 4.Pencils 5.Eraser Syllabus: 1. Understanding Lines, Shapes and Curves. 2. Creation of 2D shapes. 3. Symmetrical and Asymmetrical Concepts. \r\n\r\n4. Shading Techniques \r\n\r\n5. Concept of light and Shadows. \r\n\r\n6. Convert 2D shapes into 3D Object. \r\n\r\n7. One and Two points perspective. \r\n\r\n8. Compositional Guidelines. \r\n\r\n9. Values and Edges. \r\n\r\n10. Sketching from life. \r\n\r\n11. Proportion Basics. \r\n\r\n12. Observing Edges for realistic Renderings. \r\n\r\n13. Basics of Painting. \r\n\r\n14. One Layer painting. \r\n\r\n15. Two Layer painting. \r\n\r\n16. Three layer Painting. \r\n\r\n17. Doodles \r\n\r\n18. Zentangles. \r\n\r\n19. Conversion of real into Comic. \r\n\r\n\r\n\r\nBanjara Gypsy brings amazing artist - Chandrima Mandal, She holds an expertise in training kids and adults.  It\'s alright if you are totally a beginner or an expert. we have designed upcoming modules depends on your comfort.  Let\'s start with Beginners level Class:  Description of the art work:  Learn the layering technique in poster colours.  It is a 3-Layer painting which has a combination of brush painting and blending method.  \r\n\r\nStationaries required:  \r\n\r\n1. Poster colours \r\n\r\n2. Paint Brush - Flat brush - min Size 11.  Medium brush - size 6 to 9  Thin brush : Size 1 and 0  \r\n\r\n3. Spare Unused Toothbrush \r\n\r\n4. Pencils \r\n\r\n5. Eraser    \r\n\r\n\r\n\r\nSyllabus:   \r\n\r\n1. Understanding Lines, Shapes and Curves. \r\n\r\n2. Creation of 2D shapes. \r\n\r\n3. Symmetrical and Asymmetrical Concepts. \r\n\r\n4. Shading Techniques \r\n\r\n5. Concept of light and Shadows. \r\n\r\n6. Convert 2D shapes into 3D Object. \r\n\r\n7. One and Two points perspective. \r\n\r\n8. Compositional Guidelines. \r\n\r\n9. Values and Edges. \r\n\r\n10. Sketching from life. \r\n\r\n11. Proportion Basics. \r\n\r\n12. Observing Edges for realistic Renderings. \r\n\r\n13. Basics of Painting. \r\n\r\n14. One Layer painting. \r\n\r\n15. Two Layer painting. \r\n\r\n16. Three layer Painting. \r\n\r\n17. Doodles \r\n\r\n18. Zentangles. \r\n', '', '', '', '', 0, 'Approved'),
(66, 'Sadhguru Live with Chetan Bhagat & Get a Free Book', 'Talks', 'Online', 'Artist Krupa Shah', '2021-07-27', '22:00:00', '1', 300, 300, 'Crossword Bookstores presents an evening with Sadhguru on his new book Karma in association with Penguin Random House India.\r\n\r\nSadhguru will be in conversation with Chetan Bhagat\r\n\r\nTickets Available on BookMyShow', '', '', '', '', 0, 'Approved'),
(67, 'Humor Session : Relatability Factor In Comedy', 'Stand Up', 'Online', 'Ruhaniyat', '2021-08-07', '11:00:00', '2', 600, 400, 'Relatability Factor In Comedy\' - In this session, the expert talk about \'Being Relatable\' factor in comedy and its connections with \'Connectivity\' with the Audience for an artist while performing.\r\n\r\nAttendees will get a chance to learn in detail many aspects of making a joke relatable to different kind of audiences. The Expert will talk and discuss, how to make the pre decided content relatable for the room-audience and how much is that important for a comedian to get a connect !\r\n\r\n\'Humor Sessions\' is an initiative by India\'s Premiere Comedy Training Academy \'Cafe Comedy\' for all the aspiring comics. This is where an expert shares an inside about comedy in a brief session.\r\n\r\nInviting all the people who are trying their hand in comedy into this open house followed by QnA.\r\n\r\nPre-requisites to attend the Session:\r\n\r\nInterest in Comedy, Curiosity & Lots of Enthusiasm.\r\n\r\nThere is a small amount of registration charge to maintain the process of gathering a group of interested people discussing the Intellectual Comedy Subjects.\r\n\r\nRegistration is Must for the Entry.', '', '', '', '', 0, 'Approved'),
(68, 'Aakash Mehta Live on Zoom', 'Stand Up', 'Online', 'Ankita Shrivastav', '2021-09-06', '11:00:00', '3', 299, 300, 'An hour of whacky jokes for chill folks on zoom!', '', '', '', '', 0, 'Approved'),
(69, 'Admission - 1st Aug (Sunday)', 'Play', 'Online', 'Kunal Roy Kapoor', '2021-08-01', '08:30:00', '3', 299, 200, 'Admission is a comedy about 4 estranged college friends who reunite on zoom during the 2020 Covid pandemic and decide to stay in touch every month, to rekindle their old days. Soon it is revealed that this isn\'t just college friends catching up- things lurk beneath.  Does friendship mean we always owe a debt? Can our past decide our future?', '', '', '', '', 0, 'Approved');

-- --------------------------------------------------------

--
-- Table structure for table `otp`
--

CREATE TABLE `otp` (
  `id` int(11) NOT NULL,
  `otp` int(4) NOT NULL,
  `ref_no` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `otp`
--

INSERT INTO `otp` (`id`, `otp`, `ref_no`) VALUES
(56, 5980, 'PG2QkwG2pG2c'),
(57, 6742, 'cyr9a4AUeIUO'),
(58, 7856, '0QnT*21rGg$G'),
(59, 8885, 'VdW*6Xn8$yx6'),
(60, 2196, 'g1vZEdEHXFfc'),
(61, 3107, 'qWwl0lVIcspO'),
(62, 6030, 'FaB1L9FPAPYn'),
(63, 9902, 'TvfXrU4X2aDa'),
(64, 4415, 'XyWC#WAMcL*t'),
(65, 4095, 'ZE7PEabXV1vC'),
(66, 7010, 'aiMEdO$m4aNV'),
(67, 1403, 'Gr*AaJ3Ize7m'),
(68, 8338, 'bhOK4uj4GG6b'),
(69, 8461, 'b7TwjN#h$Zc$'),
(70, 9093, 'yDKx#3NS92lN'),
(71, 7925, 'MOvloEZIfkIP'),
(72, 4462, 'F39#zr*gNYpu'),
(73, 3975, 'XhVRpmFFTvmL'),
(74, 4906, 'NI5gZP#sDxPw'),
(75, 5381, 'GR16P8LkH9T8'),
(76, 3136, 'UdnZQ*AJbk3n'),
(77, 9929, 'bzqW2lUyNh2r'),
(78, 5789, 'ZUs#STBpWTjY'),
(79, 7203, 'D*uUv$AXEp3A'),
(80, 7059, 'wiJ$Z$k9ojyV'),
(81, 3183, 'UOvyXv6AwKnP'),
(82, 9274, 'vFU8has3mDl6'),
(83, 4526, 'NDT6$7a#H5ZS'),
(84, 2471, 'y5pfMd2hgTaG'),
(85, 5953, '1eXAuy4ldsK8'),
(86, 9229, 'zfJOrTLAkn1v'),
(87, 6995, 'uebWRwDu27Ir'),
(88, 5740, 'XevJs558rYsr'),
(89, 5508, 'VBDl1CKqyLIV');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `u_id` int(11) NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(20) NOT NULL,
  `email` varchar(30) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'active'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`u_id`, `firstname`, `lastname`, `email`, `phone`, `status`) VALUES
(3, 'Bhaskarjyoti', 'Gogoi', 'thebhaskargogoi@gmail.com', '7002072619', 'active'),
(4, 'Test', 'User', 'testuser@test.com', '1234567890', 'active'),
(5, 'Api Test', 'Kora hoise', 'test@test.com', '123456789', 'active'),
(13, 'ifigii', 'jcjcjfi', 'ififgi@igkg.com', '99876654', 'active'),
(25, 'diku', 'gogoi', 'bgogoi.user@gmail.com', '9435576083', 'active');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`a_id`);

--
-- Indexes for table `api_access_token`
--
ALTER TABLE `api_access_token`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`booking_id`),
  ADD KEY `event_id` (`event_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `contact`
--
ALTER TABLE `contact`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`event_id`);

--
-- Indexes for table `otp`
--
ALTER TABLE `otp`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`u_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `a_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `api_access_token`
--
ALTER TABLE `api_access_token`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=92;

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `booking_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT for table `contact`
--
ALTER TABLE `contact`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `events`
--
ALTER TABLE `events`
  MODIFY `event_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- AUTO_INCREMENT for table `otp`
--
ALTER TABLE `otp`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=90;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`u_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
