Pod::Spec.new do |s|
  s.name         = "JSBridge"
  s.version      = "1.0.1"
  s.summary      = "JSBridge"

  s.description  = <<-DESC
                   JSBridge1.0
                   DESC

  s.homepage     = ""
  s.license = {
    :type => 'Copyright',
    :text => <<-LICENSE
           xujian-INC copyright
    LICENSE
  }

  s.author       = { "xujian" => "xujian1030@gmail.com" }

  s.platform     = :ios

  s.ios.deployment_target = '6.0'
  s.requires_arc = true

  s.source =  { :git => "https://github.com/xujim/JSBridge.git", :branch => 'master'}
  s.source_files = 'iOS/JSBridge/JSBridge/UIWebViewController.{h,m,c,mm}', 'iOS/JSBridge/JSBridge/JSBridge/*.{h,m,c,mm,js}'

    s.xcconfig              = {
    'CLANG_CXX_LANGUAGE_STANDARD' => 'compiler-default',
    'CLANG_CXX_LIBRARY' => 'compiler-default',
    'GCC_C_LANGUAGE_STANDARD' => 'compiler-default'
    }



end