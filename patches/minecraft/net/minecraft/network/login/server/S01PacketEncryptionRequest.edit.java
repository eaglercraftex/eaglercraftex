
# Eagler Context Redacted Diff
# Copyright (c) 2025 lax1dude. All rights reserved.

# Version: 1.0
# Author: lax1dude

> CHANGE  3 : 4  @  3 : 4

~ 

> DELETE  3  @  3 : 4

> CHANGE  3 : 4  @  3 : 4

~ 	// private PublicKey publicKey;

> CHANGE  5 : 10  @  5 : 10

~ //	public S01PacketEncryptionRequest(String serverId, PublicKey key, byte[] verifyToken) {
~ //		this.hashedServerId = serverId;
~ //		this.publicKey = key;
~ //		this.verifyToken = verifyToken;
~ //	}

> CHANGE  3 : 7  @  3 : 5

~ 		// this.publicKey =
~ 		// CryptManager.decodePublicKey(parPacketBuffer.readByteArray());
~ 		parPacketBuffer.readByteArray(1024); // skip
~ 		this.verifyToken = parPacketBuffer.readByteArray(1024);

> CHANGE  3 : 6  @  3 : 6

~ //		parPacketBuffer.writeString(this.hashedServerId);
~ //		parPacketBuffer.writeByteArray(this.publicKey.getEncoded());
~ //		parPacketBuffer.writeByteArray(this.verifyToken);

> CHANGE  10 : 13  @  10 : 13

~ //	public PublicKey getPublicKey() {
~ //		return this.publicKey;
~ //	}

> EOF
