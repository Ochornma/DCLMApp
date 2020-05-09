package org.dclm.live.ui.subscribe;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import org.dclm.live.R;
import org.dclm.live.databinding.FragmentSubscribeBinding;

public class SubscribeFragment extends Fragment {


    private FragmentSubscribeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subscribe, container, false);
        String html = "<script type=\"text/javascript\">\n" +
                "    /** This section is only needed once per page if manually copying **/\n" +
                "    if (typeof MauticSDKLoaded == 'undefined') {\n" +
                "        var MauticSDKLoaded = true;\n" +
                "        var head            = document.getElementsByTagName('head')[0];\n" +
                "        var script          = document.createElement('script');\n" +
                "        script.type         = 'text/javascript';\n" +
                "        script.src          = 'https://letter.dclmict.org/index.php/media/js/mautic-form.js';\n" +
                "        script.onload       = function() {\n" +
                "            MauticSDK.onLoad();\n" +
                "        };\n" +
                "        head.appendChild(script);\n" +
                "        var MauticDomain = 'https://letter.dclmict.org/index.php';\n" +
                "        var MauticLang   = {\n" +
                "            'submittingMessage': \"Please wait...\"\n" +
                "        }\n" +
                "    }\n" +
                "</script>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<style type=\"text/css\" scoped>\n" +
                "    .mauticform_wrapper { max-width: 600px; margin: 10px auto; font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif; font-size: 14px; color: #333333;}\n" +
                "    .mauticform-innerform {}\n" +
                "    .mauticform-post-success {}\n" +
                "    .mauticform-name { font-weight: bold; font-size: 1.5em; margin-bottom: 3px; }\n" +
                "    .mauticform-description { margin-top: 2px; margin-bottom: 10px; }\n" +
                "    .mauticform-error { margin-bottom: 10px; color: red; }\n" +
                "    .mauticform-message { margin-bottom: 10px;color: green; }\n" +
                "    .mauticform-row { display: block; margin-bottom: 20px; }\n" +
                "    .mauticform-label { font-size: 1.1em; display: block; font-weight: bold; margin-bottom: 5px; }\n" +
                "    .mauticform-row.mauticform-required .mauticform-label:after { color: #e32; content: \" *\"; display: inline; }\n" +
                "    .mauticform-helpmessage { display: block; font-size: 0.9em; margin-bottom: 3px; }\n" +
                "    .mauticform-errormsg { display: block; color: red; margin-top: 2px; }\n" +
                "    .mauticform-selectbox, .mauticform-input, .mauticform-textarea { width: 100%; padding: 0.5em 0.5em; border: 1px solid #CCC; background: #fff; box-shadow: 0px 0px 0px #fff inset; border-radius: 4px; box-sizing: border-box; }\n" +
                "    .mauticform-checkboxgrp-row {}\n" +
                "    .mauticform-checkboxgrp-label { font-weight: normal; }\n" +
                "    .mauticform-checkboxgrp-checkbox {}\n" +
                "    .mauticform-radiogrp-row {}\n" +
                "    .mauticform-radiogrp-label { font-weight: normal; }\n" +
                "    .mauticform-radiogrp-radio {}\n" +
                "    .mauticform-button-wrapper .mauticform-button.btn-default { color: #5d6c7c;background-color: #ffffff;border-color: #dddddd;}\n" +
                "    .mauticform-button-wrapper .mauticform-button { display: inline-block;margin-bottom: 0;font-weight: 600;text-align: center;vertical-align: middle;cursor: pointer;background-image: none;border: 1px solid transparent;white-space: nowrap;padding: 6px 12px;font-size: 13px;line-height: 1.3856;border-radius: 3px;-webkit-user-select: none;-moz-user-select: none;-ms-user-select: none;user-select: none;}\n" +
                "    .mauticform-button-wrapper .mauticform-button.btn-default[disabled] { background-color: #ffffff; border-color: #dddddd;}\n" +
                "</style>\n" +
                "\n" +
                "<div id=\"mauticform_wrapper_subscribetodclmradio\" class=\"mauticform_wrapper\">\n" +
                "    <form autocomplete=\"false\" role=\"form\" method=\"post\" action=\"https://letter.dclmict.org/index.php/form/submit?formId=2\" id=\"mauticform_subscribetodclmradio\" data-mautic-form=\"subscribetodclmradio\" enctype=\"multipart/form-data\" name=\"dclm-radio-subscribe\">\n" +
                "        <div class=\"mauticform-error\" id=\"mauticform_subscribetodclmradio_error\"></div>\n" +
                "        <div class=\"mauticform-message\" id=\"mauticform_subscribetodclmradio_message\"></div>\n" +
                "        <div class=\"mauticform-innerform\">\n" +
                "\n" +
                "\n" +
                "          <div class=\"mauticform-page-wrapper mauticform-page-1\" data-mautic-form-page=\"1\">\n" +
                "\n" +
                "            <div id=\"mauticform_subscribetodclmradio_first_name\" data-validate=\"first_name\" data-validation-type=\"text\" class=\"mauticform-row mauticform-text mauticform-field-1 mauticform-required\">\n" +
                "                <label id=\"mauticform_label_subscribetodclmradio_first_name\" for=\"mauticform_input_subscribetodclmradio_first_name\" class=\"mauticform-label\">First Name</label>\n" +
                "                <input id=\"mauticform_input_subscribetodclmradio_first_name\" name=\"mauticform[first_name]\" value=\"\" placeholder=\"Your name\" class=\"mauticform-input\" type=\"text\">\n" +
                "                <span class=\"mauticform-errormsg\" style=\"display: none;\">This is required.</span>\n" +
                "            </div>\n" +
                "\n" +
                "            <div id=\"mauticform_subscribetodclmradio_last_name\" data-validate=\"last_name\" data-validation-type=\"text\" class=\"mauticform-row mauticform-text mauticform-field-2 mauticform-required\">\n" +
                "                <label id=\"mauticform_label_subscribetodclmradio_last_name\" for=\"mauticform_input_subscribetodclmradio_last_name\" class=\"mauticform-label\">Last Name</label>\n" +
                "                <input id=\"mauticform_input_subscribetodclmradio_last_name\" name=\"mauticform[last_name]\" value=\"\" placeholder=\"Your surname\" class=\"mauticform-input\" type=\"text\">\n" +
                "                <span class=\"mauticform-errormsg\" style=\"display: none;\">This is required.</span>\n" +
                "            </div>\n" +
                "\n" +
                "            <div id=\"mauticform_subscribetodclmradio_email_address\" data-validate=\"email_address\" data-validation-type=\"text\" class=\"mauticform-row mauticform-text mauticform-field-3 mauticform-required\">\n" +
                "                <label id=\"mauticform_label_subscribetodclmradio_email_address\" for=\"mauticform_input_subscribetodclmradio_email_address\" class=\"mauticform-label\">Email Address</label>\n" +
                "                <input id=\"mauticform_input_subscribetodclmradio_email_address\" name=\"mauticform[email_address]\" value=\"\" placeholder=\"someone@domain.com\" class=\"mauticform-input\" type=\"text\">\n" +
                "                <span class=\"mauticform-errormsg\" style=\"display: none;\">This is required.</span>\n" +
                "            </div>\n" +
                "\n" +
                "            <div id=\"mauticform_subscribetodclmradio_phone_number\" data-validate=\"phone_number\" data-validation-type=\"text\" class=\"mauticform-row mauticform-text mauticform-field-4 mauticform-required\">\n" +
                "                <label id=\"mauticform_label_subscribetodclmradio_phone_number\" for=\"mauticform_input_subscribetodclmradio_phone_number\" class=\"mauticform-label\">Phone Number</label>\n" +
                "                <input id=\"mauticform_input_subscribetodclmradio_phone_number\" name=\"mauticform[phone_number]\" value=\"\" placeholder=\"+234 8111 111  111\" class=\"mauticform-input\" type=\"text\">\n" +
                "                <span class=\"mauticform-errormsg\" style=\"display: none;\">This is required.</span>\n" +
                "            </div>\n" +
                "\n" +
                "            <div id=\"mauticform_subscribetodclmradio_country\" data-validate=\"country\" data-validation-type=\"country\" class=\"mauticform-row mauticform-select mauticform-field-5 mauticform-required\">\n" +
                "                <label id=\"mauticform_label_subscribetodclmradio_country\" for=\"mauticform_input_subscribetodclmradio_country\" class=\"mauticform-label\">Country</label>\n" +
                "                <select id=\"mauticform_input_subscribetodclmradio_country\" name=\"mauticform[country]\" value=\"Your country where you live in presently\" class=\"mauticform-selectbox\">                    <option value=\"Afghanistan\">Afghanistan</option>                    <option value=\"Åland Islands\">Åland Islands</option>                    <option value=\"Albania\">Albania</option>                    <option value=\"Algeria\">Algeria</option>                    <option value=\"Andorra\">Andorra</option>                    <option value=\"Angola\">Angola</option>                    <option value=\"Anguilla\">Anguilla</option>                    <option value=\"Antarctica\">Antarctica</option>                    <option value=\"Antigua and Barbuda\">Antigua and Barbuda</option>                    <option value=\"Argentina\">Argentina</option>                    <option value=\"Armenia\">Armenia</option>                    <option value=\"Aruba\">Aruba</option>                    <option value=\"Australia\">Australia</option>                    <option value=\"Austria\">Austria</option>                    <option value=\"Azerbaijan\">Azerbaijan</option>                    <option value=\"Bahamas\">Bahamas</option>                    <option value=\"Bahrain\">Bahrain</option>                    <option value=\"Bangladesh\">Bangladesh</option>                    <option value=\"Barbados\">Barbados</option>                    <option value=\"Belarus\">Belarus</option>                    <option value=\"Belgium\">Belgium</option>                    <option value=\"Belize\">Belize</option>                    <option value=\"Benin\">Benin</option>                    <option value=\"Bermuda\">Bermuda</option>                    <option value=\"Bhutan\">Bhutan</option>                    <option value=\"Bolivia\">Bolivia</option>                    <option value=\"Bonaire, Saint Eustatius and Saba\">Bonaire, Saint Eustatius and Saba</option>                    <option value=\"Bosnia and Herzegovina\">Bosnia and Herzegovina</option>                    <option value=\"Botswana\">Botswana</option>                    <option value=\"Bouvet Island\">Bouvet Island</option>                    <option value=\"Brazil\">Brazil</option>                    <option value=\"Brunei\">Brunei</option>                    <option value=\"Bulgaria\">Bulgaria</option>                    <option value=\"Burkina Faso\">Burkina Faso</option>                    <option value=\"Burundi\">Burundi</option>                    <option value=\"Cape Verde\">Cape Verde</option>                    <option value=\"Cambodia\">Cambodia</option>                    <option value=\"Cameroon\">Cameroon</option>                    <option value=\"Canada\">Canada</option>                    <option value=\"Cayman Islands\">Cayman Islands</option>                    <option value=\"Central African Republic\">Central African Republic</option>                    <option value=\"Chad\">Chad</option>                    <option value=\"Chile\">Chile</option>                    <option value=\"China\">China</option>                    <option value=\"Colombia\">Colombia</option>                    <option value=\"Comoros\">Comoros</option>                    <option value=\"Cook Islands\">Cook Islands</option>                    <option value=\"Costa Rica\">Costa Rica</option>                    <option value=\"Croatia\">Croatia</option>                    <option value=\"Cuba\">Cuba</option>                    <option value=\"Cyprus\">Cyprus</option>                    <option value=\"Czech Republic\">Czech Republic</option>                    <option value=\"Denmark\">Denmark</option>                    <option value=\"Djibouti\">Djibouti</option>                    <option value=\"Dominica\">Dominica</option>                    <option value=\"Dominican Republic\">Dominican Republic</option>                    <option value=\"Democratic Republic of the Congo\">Democratic Republic of the Congo</option>                    <option value=\"East Timor\">East Timor</option>                    <option value=\"Ecuador\">Ecuador</option>                    <option value=\"Egypt\">Egypt</option>                    <option value=\"El Salvador\">El Salvador</option>                    <option value=\"Equatorial Guinea\">Equatorial Guinea</option>                    <option value=\"Eritrea\">Eritrea</option>                    <option value=\"Estonia\">Estonia</option>                    <option value=\"Ethiopia\">Ethiopia</option>                    <option value=\"Falkland Islands\">Falkland Islands</option>                    <option value=\"Fiji\">Fiji</option>                    <option value=\"Finland\">Finland</option>                    <option value=\"France\">France</option>                    <option value=\"French Guiana\">French Guiana</option>                    <option value=\"French Polynesia\">French Polynesia</option>                    <option value=\"Gabon\">Gabon</option>                    <option value=\"Gambia\">Gambia</option>                    <option value=\"Georgia\">Georgia</option>                    <option value=\"Germany\">Germany</option>                    <option value=\"Ghana\">Ghana</option>                    <option value=\"Gibraltar\">Gibraltar</option>                    <option value=\"Greece\">Greece</option>                    <option value=\"Greenland\">Greenland</option>                    <option value=\"Grenada\">Grenada</option>                    <option value=\"Guadeloupe\">Guadeloupe</option>                    <option value=\"Guam\">Guam</option>                    <option value=\"Guatemala\">Guatemala</option>                    <option value=\"Guernsey\">Guernsey</option>                    <option value=\"Guinea\">Guinea</option>                    <option value=\"Guinea Bissau\">Guinea Bissau</option>                    <option value=\"Guyana\">Guyana</option>                    <option value=\"Haiti\">Haiti</option>                    <option value=\"Heard Island and McDonald Islands\">Heard Island and McDonald Islands</option>                    <option value=\"Holy See\">Holy See</option>                    <option value=\"Honduras\">Honduras</option>                    <option value=\"Hong Kong\">Hong Kong</option>                    <option value=\"Hungary\">Hungary</option>                    <option value=\"Iceland\">Iceland</option>                    <option value=\"India\">India</option>                    <option value=\"Indonesia\">Indonesia</option>                    <option value=\"Iran\">Iran</option>                    <option value=\"Iraq\">Iraq</option>                    <option value=\"Ireland\">Ireland</option>                    <option value=\"Israel\">Israel</option>                    <option value=\"Italy\">Italy</option>                    <option value=\"Ivory Coast\">Ivory Coast</option>                    <option value=\"Jamaica\">Jamaica</option>                    <option value=\"Japan\">Japan</option>                    <option value=\"Jersey\">Jersey</option>                    <option value=\"Jordan\">Jordan</option>                    <option value=\"Kazakhstan\">Kazakhstan</option>                    <option value=\"Kenya\">Kenya</option>                    <option value=\"Kiribati\">Kiribati</option>                    <option value=\"Kuwait\">Kuwait</option>                    <option value=\"Kyrgyzstan\">Kyrgyzstan</option>                    <option value=\"Laos\">Laos</option>                    <option value=\"Latvia\">Latvia</option>                    <option value=\"Lebanon\">Lebanon</option>                    <option value=\"Lesotho\">Lesotho</option>                    <option value=\"Liberia\">Liberia</option>                    <option value=\"Libya\">Libya</option>                    <option value=\"Liechtenstein\">Liechtenstein</option>                    <option value=\"Lithuania\">Lithuania</option>                    <option value=\"Luxembourg\">Luxembourg</option>                    <option value=\"Macao\">Macao</option>                    <option value=\"Macedonia\">Macedonia</option>                    <option value=\"Madagascar\">Madagascar</option>                    <option value=\"Malawi\">Malawi</option>                    <option value=\"Malaysia\">Malaysia</option>                    <option value=\"Maldives\">Maldives</option>                    <option value=\"Mali\">Mali</option>                    <option value=\"Malta\">Malta</option>                    <option value=\"Marshall Islands\">Marshall Islands</option>                    <option value=\"Martinique\">Martinique</option>                    <option value=\"Mauritania\">Mauritania</option>                    <option value=\"Mauritius\">Mauritius</option>                    <option value=\"Mayotte\">Mayotte</option>                    <option value=\"Mexico\">Mexico</option>                    <option value=\"Micronesia\">Micronesia</option>                    <option value=\"Moldova\">Moldova</option>                    <option value=\"Monaco\">Monaco</option>                    <option value=\"Mongolia\">Mongolia</option>                    <option value=\"Montenegro\">Montenegro</option>                    <option value=\"Montserrat\">Montserrat</option>                    <option value=\"Morocco\">Morocco</option>                    <option value=\"Mozambique\">Mozambique</option>                    <option value=\"Myanmar\">Myanmar</option>                    <option value=\"Namibia\">Namibia</option>                    <option value=\"Nauru\">Nauru</option>                    <option value=\"Nepal\">Nepal</option>                    <option value=\"Netherlands\">Netherlands</option>                    <option value=\"New Caledonia\">New Caledonia</option>                    <option value=\"New Zealand\">New Zealand</option>                    <option value=\"Nicaragua\">Nicaragua</option>                    <option value=\"Niger\">Niger</option>                    <option value=\"Nigeria\">Nigeria</option>                    <option value=\"Niue\">Niue</option>                    <option value=\"North Korea\">North Korea</option>                    <option value=\"Northern Mariana Islands\">Northern Mariana Islands</option>                    <option value=\"Norway\">Norway</option>                    <option value=\"Oman\">Oman</option>                    <option value=\"Pakistan\">Pakistan</option>                    <option value=\"Palau\">Palau</option>                    <option value=\"Palestine\">Palestine</option>                    <option value=\"Panama\">Panama</option>                    <option value=\"Papua New Guinea\">Papua New Guinea</option>                    <option value=\"Paraguay\">Paraguay</option>                    <option value=\"Peru\">Peru</option>                    <option value=\"Philippines\">Philippines</option>                    <option value=\"Pitcairn\">Pitcairn</option>                    <option value=\"Poland\">Poland</option>                    <option value=\"Portugal\">Portugal</option>                    <option value=\"Puerto Rico\">Puerto Rico</option>                    <option value=\"Qatar\">Qatar</option>                    <option value=\"Republic of the Congo\">Republic of the Congo</option>                    <option value=\"Réunion\">Réunion</option>                    <option value=\"Romania\">Romania</option>                    <option value=\"Russia\">Russia</option>                    <option value=\"Rwanda\">Rwanda</option>                    <option value=\"Saint Barthelemy\">Saint Barthelemy</option>                    <option value=\"Saint Helena, Ascension and Tristan da Cunha\">Saint Helena, Ascension and Tristan da Cunha</option>                    <option value=\"Saint Kitts and Nevis\">Saint Kitts and Nevis</option>                    <option value=\"Saint Lucia\">Saint Lucia</option>                    <option value=\"Saint Martin\">Saint Martin</option>                    <option value=\"Saint Pierre and Miquelon\">Saint Pierre and Miquelon</option>                    <option value=\"Saint Vincent and the Grenadines\">Saint Vincent and the Grenadines</option>                    <option value=\"Samoa\">Samoa</option>                    <option value=\"San Marino\">San Marino</option>                    <option value=\"Sao Tome and Principe\">Sao Tome and Principe</option>                    <option value=\"Saudi Arabia\">Saudi Arabia</option>                    <option value=\"Senegal\">Senegal</option>                    <option value=\"Serbia\">Serbia</option>                    <option value=\"Seychelles\">Seychelles</option>                    <option value=\"Sierra Leone\">Sierra Leone</option>                    <option value=\"Singapore\">Singapore</option>                    <option value=\"Slovakia\">Slovakia</option>                    <option value=\"Slovenia\">Slovenia</option>                    <option value=\"Solomon Islands\">Solomon Islands</option>                    <option value=\"Somalia\">Somalia</option>                    <option value=\"South Africa\">South Africa</option>                    <option value=\"South Georgia and the South Sandwich Islands\">South Georgia and the South Sandwich Islands</option>                    <option value=\"South Korea\">South Korea</option>                    <option value=\"South Sudan\">South Sudan</option>                    <option value=\"Spain\">Spain</option>                    <option value=\"Sri Lanka\">Sri Lanka</option>                    <option value=\"Svalbard and Jan Mayen\">Svalbard and Jan Mayen</option>                    <option value=\"Sudan\">Sudan</option>                    <option value=\"Suriname\">Suriname</option>                    <option value=\"Swaziland\">Swaziland</option>                    <option value=\"Sweden\">Sweden</option>                    <option value=\"Switzerland\">Switzerland</option>                    <option value=\"Syria\">Syria</option>                    <option value=\"Tahiti\">Tahiti</option>                    <option value=\"Taiwan\">Taiwan</option>                    <option value=\"Tajikistan\">Tajikistan</option>                    <option value=\"Tanzania\">Tanzania</option>                    <option value=\"Thailand\">Thailand</option>                    <option value=\"Togo\">Togo</option>                    <option value=\"Tokelau\">Tokelau</option>                    <option value=\"Tonga\">Tonga</option>                    <option value=\"Trinidad and Tobago\">Trinidad and Tobago</option>                    <option value=\"Tunisia\">Tunisia</option>                    <option value=\"Turkey\">Turkey</option>                    <option value=\"Turkmenistan\">Turkmenistan</option>                    <option value=\"Turks and Caicos Islands\">Turks and Caicos Islands</option>                    <option value=\"Tuvalu\">Tuvalu</option>                    <option value=\"United Kingdom\">United Kingdom</option>                    <option value=\"United States\">United States</option>                    <option value=\"Unknown\">Unknown</option>                    <option value=\"Uganda\">Uganda</option>                    <option value=\"Ukraine\">Ukraine</option>                    <option value=\"United Arab Emirates\">United Arab Emirates</option>                    <option value=\"Uruguay\">Uruguay</option>                    <option value=\"Uzbekistan\">Uzbekistan</option>                    <option value=\"Vanuatu\">Vanuatu</option>                    <option value=\"Venezuela\">Venezuela</option>                    <option value=\"Vietnam\">Vietnam</option>                    <option value=\"Virgin Islands (British)\">Virgin Islands (British)</option>                    <option value=\"Virgin Islands (U.S.)\">Virgin Islands (U.S.)</option>                    <option value=\"Wallis and Futuna\">Wallis and Futuna</option>                    <option value=\"Western Sahara\">Western Sahara</option>                    <option value=\"Yemen\">Yemen</option>                    <option value=\"Yugoslavia\">Yugoslavia</option>                    <option value=\"Zambia\">Zambia</option>                    <option value=\"Zimbabwe\">Zimbabwe</option>\n" +
                "                </select>\n" +
                "                <span class=\"mauticform-errormsg\" style=\"display: none;\">This is required.</span>\n" +
                "            </div>\n" +
                "\n" +
                "            <div id=\"mauticform_subscribetodclmradio_dclm_radio\" class=\"mauticform-row mauticform-freehtml mauticform-field-6\">\n" +
                "                <h3 id=\"mauticform_label_subscribetodclmradio_dclm_radio\" for=\"mauticform_input_subscribetodclmradio_dclm_radio\" class=\"mauticform-label\">\n" +
                "                    \n" +
                "                </h3>\n" +
                "                <div id=\"mauticform_input_subscribetodclmradio_dclm_radio\" name=\"mauticform[dclm_radio]\" value=\"\" class=\"mauticform-freehtml\">\n" +
                "                    You can unsubscribe at any time by clicking the link in the footer of our emails. For information about our privacy practices, please visit our website.\n" +
                "                </div>\n" +
                "            </div>\n" +
                "\n" +
                "            <div id=\"mauticform_subscribetodclmradio_submit\" class=\"mauticform-row mauticform-button-wrapper mauticform-field-7\">\n" +
                "                <button type=\"submit\" name=\"mauticform[submit]\" id=\"mauticform_input_subscribetodclmradio_submit\" value=\"\" class=\"mauticform-button btn btn-default\">Submit</button>\n" +
                "            </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <input type=\"hidden\" name=\"mauticform[formId]\" id=\"mauticform_subscribetodclmradio_id\" value=\"2\">\n" +
                "        <input type=\"hidden\" name=\"mauticform[return]\" id=\"mauticform_subscribetodclmradio_return\" value=\"\">\n" +
                "        <input type=\"hidden\" name=\"mauticform[formName]\" id=\"mauticform_subscribetodclmradio_name\" value=\"subscribetodclmradio\">\n" +
                "\n" +
                "        </form>\n" +
                "</div>\n" +
                "\n";

        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.setWebViewClient(new MyWebViewClient());
        binding.webview.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);

        return binding.getRoot();
    }

  private static class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                view.loadUrl(url);
                return false;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }
    }
}
