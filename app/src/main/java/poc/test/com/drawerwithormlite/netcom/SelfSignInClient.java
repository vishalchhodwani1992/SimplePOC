package poc.test.com.drawerwithormlite.netcom;


import android.content.Context;
import android.os.Build;


import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;

public class SelfSignInClient {

    private Context context;

    public SelfSignInClient(Context context) {
        this.context = context;
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        Certificate certificate = getCertificate();
        KeyStore keyStore = createKeyStoreTrustedCAs(certificate);
        TrustManagerFactory managerFactory = createTrustManagerCAs(keyStore);
        SSLContext sslContext = createSSLSocketFactory(managerFactory);



      //set for kitkat device android
        SSLSocketFactory NoSSLv3Factory = new Tls12SocketFactory(sslContext.getSocketFactory());
        okHttpClient.sslSocketFactory(NoSSLv3Factory);
        okHttpClient.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return hostname.equals("www.cdnsolutionsgroup.com");
            }
        });

        okHttpClient.connectTimeout(60, TimeUnit.SECONDS);
        okHttpClient.readTimeout(60, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(60, TimeUnit.SECONDS);
        // If you need an Interceptor to add some header
//        okHttpClient.addInterceptor();
        return okHttpClient.build();
    }

    // creating an SSLSocketFactory that uses our TrustManager
    public SSLContext createSSLSocketFactory(TrustManagerFactory managerFactory) {
        final String PROTOCOL = "TLS";
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance(PROTOCOL);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            assert sslContext != null;
            sslContext.init(null, managerFactory.getTrustManagers(), null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    // creating a TrustManager that trusts the CAs in our KeyStore
    public TrustManagerFactory createTrustManagerCAs(KeyStore keyStore) {
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory managerFactory = null;
        try {
            managerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            assert managerFactory != null;
            managerFactory.init(keyStore);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return managerFactory;
    }

    // creating a KeyStore containing our trusted CAs
    public KeyStore createKeyStoreTrustedCAs(Certificate certificate) {
        final String ALIAS_CA = "ca";
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(keyStoreType);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            assert keyStore != null;
            keyStore.load(null, null);
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
        try {
            keyStore.setCertificateEntry(ALIAS_CA, certificate);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return keyStore;
    }

    // creating a Certificate
    public Certificate getCertificate() {
        Certificate certificate = null;
        CertificateFactory certificateFactory = loadCertificateAuthorityFromResources();
        InputStream inputStream = getCAFromResources();
        try {
            certificate = certificateFactory.generateCertificate(inputStream);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return certificate;
    }

    // loading CAs from an InputStream
    private CertificateFactory loadCertificateAuthorityFromResources() {
        final String CERT_TYPE = "X.509";
        InputStream certificateAuthority = getCAFromResources();
        CertificateFactory certificateFactory = null;
        try {
            certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        try {
            assert certificateFactory != null;
            certificateFactory.generateCertificate(certificateAuthority);
        } catch (CertificateException e) {
            e.printStackTrace();
        } finally {
            try {
                certificateAuthority.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return certificateFactory;
    }

    private InputStream getCAFromResources() {
/*
        return context.getResources().openRawResource(R.raw.cdnsolutionsgroupcom);
*/
        return null;

    }


    public static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client) {
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
            try {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);
                client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));

                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();

                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                client.connectionSpecs(specs);
            } catch (Exception exc) {
            }
        }

        return client;
    }

    public OkHttpClient getNewHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS);

        return enableTls12OnPreLollipop(client).build();
    }
}
