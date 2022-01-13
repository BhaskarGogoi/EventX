<?php
    header('Content-Type: application/json');

    include('../includes/database_connection.php');


	$phone = mysqli_real_escape_string($conn, $_POST['phone']);
	if (empty($phone)) {
		echo json_encode([
			"response"=>400,
			"msg"=>"Phone is Empty!"
		]);
		exit();
	}
    function generateRandomString($length = 12) {
        $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$*#';
        $charactersLength = strlen($characters);
        $randomString = '';
        for ($i = 0; $i < $length; $i++) {
            $randomString .= $characters[rand(0, $charactersLength - 1)];
        }
        return $randomString;
    }
    $ref_no = generateRandomString();
    $otp = rand(1111,9999);
    
//    sending otp
    $fields = array(
        "variables_values" => $otp,
        "route" => "otp",
        "numbers" => $phone,
    );
    
    $curl = curl_init();
    
    curl_setopt_array($curl, array(
      CURLOPT_URL => "https://www.fast2sms.com/dev/bulkV2",
      CURLOPT_RETURNTRANSFER => true,
      CURLOPT_ENCODING => "",
      CURLOPT_MAXREDIRS => 10,
      CURLOPT_TIMEOUT => 30,
      CURLOPT_SSL_VERIFYHOST => 0,
      CURLOPT_SSL_VERIFYPEER => 0,
      CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
      CURLOPT_CUSTOMREQUEST => "POST",
      CURLOPT_POSTFIELDS => json_encode($fields),
      CURLOPT_HTTPHEADER => array(
        "authorization: YOT8Bn1zaKDEchSPJsmAfZ6ij3qQNCe2ypoVlrFu9dGw50UbLRwcsqoF9HMNDgAB2leOTpnQKm50PE1v",
        "accept: */*",
        "cache-control: no-cache",
        "content-type: application/json"
      ),
    ));
    
    $response = curl_exec($curl);
    $err = curl_error($curl);
    
    
    curl_close($curl);
    
    if ($err) {
        echo json_encode([
            "response_code"=>500,
            "msg" => $err
        ]);
    } else {
       //saving otp to database
        $sql = "INSERT INTO otp ( otp, ref_no)
        VALUES (?, ?)";
        $stmt = mysqli_stmt_init($conn);
        if(!mysqli_stmt_prepare($stmt, $sql)){
            echo json_encode([
                "response_code"=>500
            ]);
        } else {
            mysqli_stmt_bind_param($stmt, "ss", $otp, $ref_no);
            if(mysqli_stmt_execute($stmt)){
                echo json_encode([
                    "response_code"=>201,
                    "ref_no"=>$ref_no
                ]);
            }else {
                echo json_encode([
                    "response_code"=>500
                ]);
            }  
        }
    }
?>