local key = KEYS[1]

local capacity = tonumber(ARGV[1])
local refill_rate = tonumber(ARGV[2])
local now = tonumber(ARGV[3])

local bucket = redis.call("HMGET", key, "tokens", "timestamp")

local tokens = tonumber(bucket[1])
local last_refill = tonumber(bucket[2])

if tokens == nil then
  tokens = capacity
  last_refill = now
end

local delta = math.max(0, now - last_refill)
local refill = delta * refill_rate

tokens = math.min(capacity, tokens + refill)

local allowed = 0

if tokens >= 1 then
  tokens = tokens - 1
  allowed = 1
end

redis.call("HMSET", key, "tokens", tokens, "timestamp", now)

local reset_time = now + math.ceil((capacity - tokens) / refill_rate)

return {allowed, tokens, reset_time}