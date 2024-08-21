# 이미지 업로드 프로젝트

이 프로젝트는 React 빌드파일과 이미지를 각각의 AWS S3와 CloudFront에 저장 및 배포하고 캐싱하여 가져오는 기능을 포함합니다. 또한, CI/CD 파이프라인을 구현하여 자동 배포를 연습합니다. 주요 기능으로는 이미지 업로드, 조회, 삭제가 있으며, AWS 서비스를 활용한 배포와 관리가 포함됩니다.

## 🛠️ 기능

- **이미지 업로드**: 사용자가 React 프론트엔드를 통해 이미지를 업로드할 수 있습니다.
- **이미지 조회**: 업로드된 이미지를 S3에 저장하고, CloudFront를 통해 캐싱된 이미지를 가져와 표시할 수 있습니다.
- **이미지 삭제**: 사용자가 이전에 업로드한 이미지를 삭제할 수 있습니다.
- **AWS S3 저장**:
  - **React 빌드 파일**: S3 버킷에 저장하여 정적 파일을 호스팅합니다.
  - **이미지 파일**: 사용자 업로드 이미지를 S3에 저장합니다.
- **CloudFront 캐싱**:
  - **React 빌드 파일**: S3에서 제공하는 정적 파일을 CloudFront로 캐싱하여 빠르게 제공합니다.
  - **이미지 파일**: S3에 저장된 이미지를 CloudFront로 캐싱하여 빠르게 제공합니다.
- **EC2 배포**: 백엔드는 EC2 인스턴스에 배포됩니다.
- **CI/CD 연습**: 자동 배포를 위한 CI/CD 파이프라인이 구현되어 있습니다.

## 🚀 배포 단계

### 1. 프론트엔드 설정

- [React 프론트엔드 리포지토리](https://github.com/nicolao00/ImageUpload-front)를 클론합니다.
- S3 버킷에 정적 파일을 호스팅하도록 프로젝트를 설정합니다.
- React 앱을 S3에 배포합니다:
  ```bash
  npm run build
  aws s3 sync build/ s3://your-react-app-s3-bucket --acl public-read
