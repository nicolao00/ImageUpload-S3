# 이미지 업로드 프로젝트

이 프로젝트는 React 빌드파일과 이미지를 각각의 AWS S3와 CloudFront에 저장 및 배포하고 캐싱하여 가져오는 기능을 포함합니다. 또한, CI/CD 파이프라인을 구현하여 자동 배포를 연습합니다. 주요 기능으로는 이미지 업로드, 조회, 삭제가 있으며, AWS 서비스를 활용한 배포와 관리가 포함됩니다.

## 📷 시연영상
<p align="center">
  <img src = "https://github.com/user-attachments/assets/1be53404-708a-4895-a479-fe0c9982921a">
</p>

## 🛠️ 기능

- **이미지 업로드**: 사용자가 이미지를 업로드할 수 있습니다.
- **이미지 조회**: 업로드된 이미지를 조회하여 화면에 표시할 수 있습니다.
- **이미지 삭제**: 업로드된 이미지를 삭제할 수 있습니다.

## 🌐 배포

- **정적 파일 배포**: 이 애플리케이션의 정적 파일은 AWS S3에 호스팅되며, CloudFront를 통해 캐싱되어 빠르게 제공됩니다.
- **이미지 관리**: 업로드된 이미지는 별도의 S3 버킷에 저장되며, CloudFront를 통해 캐싱되어 빠르게 조회할 수 있습니다.
  #### 🔗 배포 주소
  애플리케이션은 다음 주소에서 확인할 수 있습니다: [Image Upload Frontend 배포 주소](https://uploader-front.p-e.kr/)
  #### 🔐 HTTPS 및 SSL 인증서 설정
  **Nginx 설정**: Nginx를 사용하여 SSL 인증서를 적용하고 HTTPS를 설정했습니다. 이를 통해 애플리케이션은 암호화된 연결을 통해 안전하게 제공됩니다.


## 🚀 배포 단계

### 1. 프론트엔드 설정

- [React 프론트엔드 리포지토리](https://github.com/nicolao00/ImageUpload-front)를 클론합니다.
- S3 버킷에 정적 파일을 호스팅하도록 프로젝트를 설정합니다.
- React 앱을 S3에 배포합니다:
  ```bash
  npm run build
  aws s3 sync build/ s3://your-react-app-s3-bucket --acl public-read
