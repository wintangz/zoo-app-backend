package net.wintang.zooapp.service;

import jakarta.servlet.http.HttpServletRequest;
import net.wintang.zooapp.dto.response.OrderResponseDTO;
import net.wintang.zooapp.entity.Order;
import net.wintang.zooapp.repository.OrderRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentService implements IPaymentService {

    private final OrderRepository orderRepository;

    @Autowired
    public PaymentService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public ResponseEntity<ResponseObject> getPaymentUrl(OrderResponseDTO order, HttpServletRequest req) throws UnsupportedEncodingException, SignatureException, NoSuchAlgorithmException, InvalidKeyException {

        Random random = new Random();
        System.out.println("VNP 1");

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = "Pay for your Zoo tickets";
        String orderType = "250000";
        String vnp_TxnRef = String.valueOf(order.getId());
        String vnp_IpAddr = req.getRemoteAddr();
        String vnp_TmnCode = "FV7L6BYI";

        int amount = (int) order.getTotal() * 100;
        Map vnp_Params = new HashMap();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", "http://localhost:3000/thanks");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Billing
//        vnp_Params.put("vnp_Bill_Mobile", "Mobile Phone");
        vnp_Params.put("vnp_Bill_Email", String.valueOf(order.getId()));
//        String fullName = ("Tang Tuong Vinh").trim();
//        if (fullName != null && !fullName.isEmpty()) {
//            int idx = fullName.indexOf(' ');
//            String firstName = fullName.substring(0, idx);
//            String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
//            vnp_Params.put("vnp_Bill_FirstName", firstName);
//            vnp_Params.put("vnp_Bill_LastName", lastName);
//
//        }
//        vnp_Params.put("vnp_Bill_Address", "Quan 1");
//        vnp_Params.put("vnp_Bill_City", "Ho Chi Minh");
//        vnp_Params.put("vnp_Bill_Country", "Viet Nam");
        // Invoice
//        vnp_Params.put("vnp_Inv_Phone", "SDT");
//        vnp_Params.put("vnp_Inv_Email", "MAIL");
        vnp_Params.put("vnp_Inv_Customer", String.valueOf(order.getId()));
//        vnp_Params.put("vnp_Inv_Address", "DIA CHI");
//        vnp_Params.put("vnp_Inv_Company", "CTY");
//        vnp_Params.put("vnp_Inv_Taxcode", "MST");
//        vnp_Params.put("vnp_Inv_Type", "LOAI");
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = String.valueOf(itr.next());
            String fieldValue = String.valueOf(vnp_Params.get(fieldName));
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        System.out.println("HashDATA: " + hashData.toString());
        String vnp_SecureHash = calculateHMAC("DIPWDUWRWSBLVQWRAGFXYEOVAZBDOYVY", hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html" + "?" + queryUrl;
        System.out.println("This is url " + paymentUrl);

        System.out.println("VNP 2");

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS, paymentUrl)
        );
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public static String calculateHMAC(String key, String data)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA512");
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(secretKeySpec);
        return toHexString(mac.doFinal(data.getBytes()));
    }

    @Override
    public void setPaymentStatus(String id) {
        Order order = orderRepository.findById(Integer.parseInt(id)).orElseThrow();
        order.setStatus(true);
        orderRepository.save(order);
        //Create QR-Code and save OrderDetails

    }
}