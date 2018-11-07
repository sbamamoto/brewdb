//  SecureSocket
//
//  Created by snapper26 on 2/9/16.
//  Copyright © 2016 snapper26. All rights reserved.
//
import Foundation

class ProXimityAPIClient: NSObject, StreamDelegate {

    // Input and output streams for socket
    var inputStream: InputStream?
    var outputStream: OutputStream?

    // Secondary delegate reference to prevent ARC deallocating the NSStreamDelegate
    var inputDelegate: StreamDelegate?
    var outputDelegate: StreamDelegate?

    // Add a trusted root CA to out SecTrust object
    func addAnchorToTrust(trust: SecTrust, certificate: SecCertificate) -> SecTrust {
        let array: NSMutableArray = NSMutableArray()

        array.add(certificate)

        SecTrustSetAnchorCertificates(trust, array)

        return trust
    }

    // Create a SecCertificate object from a DER formatted certificate file
    func createCertificateFromFile(filename: String, ext: String) -> SecCertificate {
        let rootCertPath = Bundle.main.path(forResource:filename, ofType: ext)

        let rootCertData = NSData(contentsOfFile: rootCertPath!)

        return SecCertificateCreateWithData(kCFAllocatorDefault, rootCertData!)!
    }

    // Connect to remote host/server
    func connect(host: String, port: Int) {
        // Specify host and port number. Get reference to newly created socket streams both in and out
        Stream.getStreamsToHost(withName:host, port: port, inputStream: &inputStream, outputStream: &outputStream)

        // Create strong delegate reference to stop ARC deallocating the object
        inputDelegate = self
        outputDelegate = self

        // Now that we have a strong reference, assign the object to the stream delegates
        inputStream!.delegate = inputDelegate
        outputStream!.delegate = outputDelegate

        // This doesn't work because of arc memory management. Thats why another strong reference above is needed.
        //inputStream!.delegate = self
        //outputStream!.delegate = self

        // Schedule our run loops. This is needed so that we can receive StreamEvents
        inputStream!.schedule(in:RunLoop.main, forMode: RunLoopMode.defaultRunLoopMode)
        outputStream!.schedule(in:RunLoop.main, forMode: RunLoopMode.defaultRunLoopMode)

        // Enable SSL/TLS on the streams
        inputStream!.setProperty(kCFStreamSocketSecurityLevelNegotiatedSSL, forKey:  Stream.PropertyKey.socketSecurityLevelKey)
        outputStream!.setProperty(kCFStreamSocketSecurityLevelNegotiatedSSL, forKey: Stream.PropertyKey.socketSecurityLevelKey)

        // Defin custom SSL/TLS settings
        let sslSettings : [NSString: Any] = [
            // NSStream automatically sets up the socket, the streams and creates a trust object and evaulates it before you even get a chance to check the trust yourself. Only proper SSL certificates will work with this method. If you have a self signed certificate like I do, you need to disable the trust check here and evaulate the trust against your custom root CA yourself.
            NSString(format: kCFStreamSSLValidatesCertificateChain): kCFBooleanFalse,
            //
            NSString(format: kCFStreamSSLPeerName): kCFNull,
            // We are an SSL/TLS client, not a server
            NSString(format: kCFStreamSSLIsServer): kCFBooleanFalse
        ]

        // Set the SSL/TLS settingson the streams
        inputStream!.setProperty(sslSettings, forKey:  kCFStreamPropertySSLSettings as Stream.PropertyKey)
        outputStream!.setProperty(sslSettings, forKey: kCFStreamPropertySSLSettings as Stream.PropertyKey)

        // Open the streams
        inputStream!.open()
        outputStream!.open()
    }

    // This is where we get all our events (haven't finished writing this class)
   func stream(_ aStream: Stream, handle eventCode: Stream.Event) {
        switch eventCode {
        case Stream.Event.endEncountered:
            print("End Encountered")
            break
        case Stream.Event.openCompleted:
            print("Open Completed")
            break
        case Stream.Event.hasSpaceAvailable:
            print("Has Space Available")

            // If you try and obtain the trust object (aka kCFStreamPropertySSLPeerTrust) before the stream is available for writing I found that the oject is always nil!
            var sslTrustInput: SecTrust? =  inputStream! .property(forKey:kCFStreamPropertySSLPeerTrust as Stream.PropertyKey) as! SecTrust?
            var sslTrustOutput: SecTrust? = outputStream!.property(forKey:kCFStreamPropertySSLPeerTrust as Stream.PropertyKey) as! SecTrust?

            if (sslTrustInput == nil) {
                print("INPUT TRUST NIL")
            }
            else {
                print("INPUT TRUST NOT NIL")
            }

            if (sslTrustOutput == nil) {
                print("OUTPUT TRUST NIL")
            }
            else {
                print("OUTPUT TRUST NOT NIL")
            }

            // Get our certificate reference. Make sure to add your root certificate file into your project.
            let rootCert: SecCertificate? = createCertificateFromFile(filename: "ca", ext: "der")

            // TODO: Don't want to keep adding the certificate every time???
            // Make sure to add your trusted root CA to the list of trusted anchors otherwise trust evaulation will fail
            sslTrustInput  = addAnchorToTrust(trust: sslTrustInput!,  certificate: rootCert!)
            sslTrustOutput = addAnchorToTrust(trust: sslTrustOutput!, certificate: rootCert!)

            // convert kSecTrustResultUnspecified type to SecTrustResultType for comparison
            var result: SecTrustResultType = SecTrustResultType.unspecified

            // This is it! Evaulate the trust.
            let error: OSStatus = SecTrustEvaluate(sslTrustInput!, &result)

            // An error occured evaluating the trust check the OSStatus codes for Apple at osstatus.com
            if (error != noErr) {
                print("Evaluation Failed")
            }

            if (result != SecTrustResultType.proceed && result != SecTrustResultType.unspecified) {
                // Trust failed. This will happen if you faile to add the trusted anchor as mentioned above
                print("Peer is not trusted :(")
            }
            else {
                // Peer certificate is trusted. Now we can send data. Woohoo!
                print("Peer is trusted :)")
            }

            break
        case Stream.Event.hasBytesAvailable:
            print("Has Bytes Available")
            break
        case Stream.Event.errorOccurred:
            print("Error Occured")
            break
        default:
            print("Default")
            break
        }
    }
}