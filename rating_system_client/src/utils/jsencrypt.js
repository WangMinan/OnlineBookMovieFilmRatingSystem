import JSEncrypt from 'jsencrypt'
// 密钥对生成 http://web.chacuo.net/netrsakeypair
const publicKey =
  'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFhj1+837m2zHrbWTiPq61omU5\n' +
  'TNwu/TfmTgoDy+YILgpXAI6Z3kTlq/3AbHq68jKt6n/EOKp+ld0X1Uk44dWI6O0b\n' +
  'C6tc1olZVjASQBhCFrntIB+UH5kxw3n9J+1Vn1o/CceCRevTdn2g1cM7sOKeQsLk\n' +
  'k7eAUr1YumCDPcwHJwIDAQAB'
const privateKey =
  'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMWGPX7zfubbMett\n' +
  'ZOI+rrWiZTlM3C79N+ZOCgPL5gguClcAjpneROWr/cBserryMq3qf8Q4qn6V3RfV\n' +
  'STjh1Yjo7RsLq1zWiVlWMBJAGEIWue0gH5QfmTHDef0n7VWfWj8Jx4JF69N2faDV\n' +
  'wzuw4p5CwuSTt4BSvVi6YIM9zAcnAgMBAAECgYEAkyiSBFTwxJmltVqJAK3mh9f3\n' +
  'RhtnJ3LsBJ87gtyUAqAaf/sgQq5/8gSTRpsLTOBItZ2xlsUI8MpCMBFtPf2ykcBX\n' +
  'AbLLjOjhd9CnK3GpNWgTUOy4TPPOUHWc1JpT63Og1NIe0zZPp2PFJomddqoHsmJr\n' +
  'mXOp1G7COLQmu7jcJlECQQDruT6JhSWMimtllxVYvmxa2SghtJf1RPyshqsXrWMd\n' +
  'kkfzUnV/Gfl7haBvuXmilz88tZvDIxq5OM1ecba/EkOLAkEA1oPUPF4RLgxOZDP2\n' +
  'k3/s/E/gT2rhwq/gYeOBl2dlyMgkHvwAIoG0947SvNJxGCuooDMJFVof3P49XwTK\n' +
  'AF4OVQJBAM//3KvyXii+N3gkMcTl+qzLc7sSJQhbAG6oQi6ZY51DZ9PQTjeCTv9s\n' +
  '8R3E1wzeUwGZ6sDsp/cKpUUSKEQ/tHMCQAeI7eWI8FWLvR0r7hdUQQDD1X80o+Vw\n' +
  'xBLsKNsmmJ6q2Qn1lBOiNrcTXzeetLZColOuNz12wvPCQmmaA3Zr11kCQDc+swsz\n' +
  'EjTFWeLvuvZ+sxSn8yxUsmsR1nwjtxa6hTd5KXBIkif0VLZfzd68A/3tzZ7sv8Lk\n' +
  'XeXhB2C6N0OcGIU='
// 加密
export function encrypt (txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey) // 设置公钥
  return encryptor.encrypt(txt) // 对数据进行加密
}
// 解密
export function decrypt (txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPrivateKey(privateKey) // 设置私钥
  return encryptor.decrypt(txt) // 对数据进行解密
}
